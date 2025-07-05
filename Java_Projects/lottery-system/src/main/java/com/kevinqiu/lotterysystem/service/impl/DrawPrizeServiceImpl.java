package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class DrawPrizeServiceImpl implements DrawPrizeService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void drawPrize(DrawPrizeParam param) {
        Map<String, String> map = new HashMap<>();
        map.put("messageId", String.valueOf(UUID.randomUUID()));
        map.put("messageInfo", JacksonUtil.writeValueAsString(param));
        rabbitTemplate.convertAndSend(DirectRabbitConfig.EXCHANGE_NAME,
                DirectRabbitConfig.ROUTING, JacksonUtil.writeValueAsString(map));
        log.info("已成功发送信息，map:{}", JacksonUtil.writeValueAsString(map));
    }

}
