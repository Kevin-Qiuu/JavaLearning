package com.kevinqiu.lotterysystem.service.mq;

import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.MailUtil;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.kevinqiu.lotterysystem.dao.mapper.WinningRecordMapper;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import com.kevinqiu.lotterysystem.service.activityStatus.ActivityStatusManager;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityUserStatusEnum;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import static com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig.*;

@Slf4j
@Component
// 表示这个类会监听 RabbitMQ 中名字为 QUEUE_NAME 的队列，当队列中有元素时就会自动获取
@RabbitListener(queues = QUEUE_NAME)
public class MqReceiver {

    @Autowired
    private DrawPrizeService drawPrizeService;
    @Autowired
    private ActivityStatusManager activityStatusManager;
    @Autowired
    private MailUtil mailUtil;
    @Resource(name = "notifyResultAsyncServiceExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;
    @Autowired
    private WinningRecordMapper winningRecordMapper;


    // @RabbitHandler 通常都会与 @RabbitListener 搭配使用，会根据监听队列中的元素自动选择对应参数的 handler 方法
    @RabbitHandler
    public void process(Map<String, String> message) {
        log.info("成功消费了一个消息，Map<String, String> message: {}", message);
        DrawPrizeParam param = JacksonUtil.readValue(message.get("messageInfo"), DrawPrizeParam.class);

        try {
            // 校验抽奖请求是否有效
            // 不设置抛出异常，为了防止当前端响应慢导致发回两个连续相同数据请求时，
            // 第二个数据会报异常，进而触发了状态回滚，导致第一个数据也失效


            if (!drawPrizeService.checkDrawPrizeParam(param)) {
                log.warn("中奖参数校验失败，DrawPrizeParam: {}", JacksonUtil.writeValueAsString(param));
                return;
            }

            // 状态扭转处理（责任链 + 策略模式）
            statusConvert(param);

            // 保存中奖者名单
            List<WinningRecordDO> winnerRecordsDOList = drawPrizeService.saveWinnerRecords(param);

            // 多线程通过邮箱通知中奖者，降低等待延迟
            threadPoolTaskExecutor.execute(() -> sendMails(winnerRecordsDOList));
        } catch (ServiceException e) {
            log.error("发生ServiceException异常，已触发状态回滚，异常码：{}，异常信息：{}", e.getCode(), e.getMessage());
            rollBack(param);
            throw e;
        } catch (Exception e) {
            log.error("发生异常，已触发回滚，e：", e);
            rollBack(param);
            throw e;
        }


    }

    private void rollBack(DrawPrizeParam param) {

        // 判断活动、关联人员和关联奖品的表是否已经进行了状态转换，如果状态发生了转换，则进行回滚
        if (!statusNeedRollBack(param)) {
            return;
        }

        rollBackStatus(param);

        // 判断活动 winning_record 是否进行了状态转换，如果状态发生了转换，则进行回滚
        if (!winningRecordNeedRollBack(param)) {
            return;
        }

        rollBackWinningRecord(param);

    }

    private void rollBackWinningRecord(DrawPrizeParam param) {
        drawPrizeService.deleteWinnerRecords(param.getActivityId(), param.getPrizeId());
    }

    private boolean winningRecordNeedRollBack(DrawPrizeParam param) {

        // 直接判断 winning_record 中是否存在当前活动下奖品的中奖记录
        return winningRecordMapper
                .countSelectByActivityIdAndPrizeId(param.getActivityId(), param.getPrizeId()) > 0;

    }

    private void rollBackStatus(DrawPrizeParam param) {

        ConvertActivityStatusDTO convertActivityStatusDTO = new ConvertActivityStatusDTO();

        convertActivityStatusDTO.setActivityId(param.getActivityId());
        convertActivityStatusDTO.setPrizeId(param.getPrizeId());
        convertActivityStatusDTO.setUserIds(param.getWinnerList()
                .stream().map(DrawPrizeParam.Winner::getUserId).toList());

        convertActivityStatusDTO.setTargetActivityStatus(ActivityStatusEnum.RUNNING);
        convertActivityStatusDTO.setTargetPrizeStatus(ActivityPrizeStatusEnum.INIT);
        convertActivityStatusDTO.setTargetUserStatus(ActivityUserStatusEnum.INIT);

        activityStatusManager.rollBackHandlerEvent(convertActivityStatusDTO);

    }


    private boolean statusNeedRollBack(DrawPrizeParam param) {
        // 这里需要判断活动、活动关联奖品和活动关联人员三个表是否已经进行了状态转换
        // 但是需要注意的是，在 ActivityStatusManager 的 handlerEvent 方法中已经设置了一致性
        // 所以只需判断奖品/人员是否进行了回滚即可

        ActivityPrizeDO activityPrizeDO = activityPrizeMapper
                .selectByActivityAndPrizeId(param.getActivityId(),param.getPrizeId());

        return activityPrizeDO.getStatus()
                .equalsIgnoreCase(ActivityPrizeStatusEnum.COMPLETED.name());
    }

    public void sendMails(List<WinningRecordDO> winnerRecordsDOList) {
        for (WinningRecordDO winningRecordDO : winnerRecordsDOList) {
            String subject = winningRecordDO.getActivityName() + "中奖通知";
            String context = "你好！恭喜你在"+winningRecordDO.getActivityName()
                    +"中获得"+ ActivityPrizeTiersEnum.forName(winningRecordDO.getPrizeTier()).getMessage()
                    +"，奖品是"+ winningRecordDO.getPrizeName() +"！祝你每天欧气满满～～";

            mailUtil.sendSimpleMail(winningRecordDO.getWinnerEmail(), subject, context);
        }
    }

    public void statusConvert(DrawPrizeParam param) {

        ConvertActivityStatusDTO convertActivityStatusDTO = new ConvertActivityStatusDTO();

        convertActivityStatusDTO.setActivityId(param.getActivityId());
        convertActivityStatusDTO.setPrizeId(param.getPrizeId());
        convertActivityStatusDTO.setUserIds(param.getWinnerList()
                .stream().map(DrawPrizeParam.Winner::getUserId).toList());

        convertActivityStatusDTO.setTargetActivityStatus(ActivityStatusEnum.FINISHED);
        convertActivityStatusDTO.setTargetPrizeStatus(ActivityPrizeStatusEnum.COMPLETED);
        convertActivityStatusDTO.setTargetUserStatus(ActivityUserStatusEnum.COMPLETED);

        activityStatusManager.handlerEvent(convertActivityStatusDTO);
    }

}
