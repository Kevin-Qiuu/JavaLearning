package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.dao.dataobject.ActivityDO;
import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityMapper;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
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
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public void drawPrize(DrawPrizeParam param) {
        Map<String, String> map = new HashMap<>();
        map.put("messageId", String.valueOf(UUID.randomUUID()));
        map.put("messageInfo", JacksonUtil.writeValueAsString(param));
        rabbitTemplate.convertAndSend(DirectRabbitConfig.EXCHANGE_NAME,
                DirectRabbitConfig.ROUTING, map);
        log.info("已成功生产信息，map:{}", JacksonUtil.writeValueAsString(map));
    }

    @Override
    public Boolean checkDrawPrizeParam(DrawPrizeParam param) {

        // 验证非空
        if (null == param) {
            return false;
        }

        // 验证活动和奖品是否存在
        ActivityDO activityDO = activityMapper.selectById(param.getActivityId());
        // 之所以可以使用 ActivityPrizeDO 判断活动奖品状态，是因为在保存活动信息时做了本地事务，保证了活动与活动关联奖品的一致性
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper
                .selectByActivityAndPrizeId(param.getActivityId(), param.getPrizeId());
        if (null == activityDO || null == activityPrizeDO) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_OR_PRIZE_IS_NULL);
        }

        // 验证活动是否有效
        if (!activityDO.getActivityName().equalsIgnoreCase(ActivityStatusEnum.RUNNING.name())) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_IS_COMPLETED);
        }

        // 验证奖品是否有效
        if (!activityPrizeDO.getStatus().equalsIgnoreCase(ActivityPrizeStatusEnum.INIT.name())) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_IS_COMPLETED);
        }

        // 验证奖品的数量是否等于中奖人数
        if (param.getWinnerList().size() != activityPrizeDO.getPrizeAmount()) {
            throw new ServiceException(ServiceErrorCodeConstants.WINNER_PRIZE_AMOUNT_ERROR);
        }

        return true;

    }
}
