package com.kevinqiu.lotterysystem.service.mq;

import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import com.kevinqiu.lotterysystem.service.activityStatus.ActivityStatusManager;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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


    // @RabbitHandler 通常都会与 @RabbitListener 搭配使用，会根据监听队列中的元素自动选择对应参数的 handler 方法
    @RabbitHandler
    public void process(Map<String, String> message) {
        log.info("成功消费了一个消息，Map<String, String> message: {}", message);
        DrawPrizeParam param = JacksonUtil.readValue(message.get("messageInfo"), DrawPrizeParam.class);

        // 校验抽奖请求是否有效
        drawPrizeService.checkDrawPrizeParam(param);

        // 状态扭转处理（责任链 + 策略模式）
        statusConvert(param);

        // 保存中奖者名单

        // 通知中奖者（邮箱、短信）
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
