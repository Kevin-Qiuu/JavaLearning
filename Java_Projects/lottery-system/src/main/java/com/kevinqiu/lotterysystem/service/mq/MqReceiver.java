package com.kevinqiu.lotterysystem.service.mq;

import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.MailUtil;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import com.kevinqiu.lotterysystem.service.activityStatus.ActivityStatusManager;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityUserStatusEnum;
import jakarta.annotation.Resource;
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


    // @RabbitHandler 通常都会与 @RabbitListener 搭配使用，会根据监听队列中的元素自动选择对应参数的 handler 方法
    @RabbitHandler
    public void process(Map<String, String> message) {
        log.info("成功消费了一个消息，Map<String, String> message: {}", message);
        DrawPrizeParam param = JacksonUtil.readValue(message.get("messageInfo"), DrawPrizeParam.class);

        try {
            // 校验抽奖请求是否有效
            drawPrizeService.checkDrawPrizeParam(param);

            // 状态扭转处理（责任链 + 策略模式）
            statusConvert(param);

            // 保存中奖者名单
            List<WinningRecordDO> winnerRecordsDOList = drawPrizeService.saveWinnerRecords(param);

            // 多线程通过邮箱通知中奖者
            threadPoolTaskExecutor.execute(() -> sendMails(winnerRecordsDOList));
        } catch (ServiceException e) {

            throw e;
        } catch (Exception e) {

            throw e;
        }


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
