package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.RedisUtil;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.controller.param.ShowWinningRecordParam;
import com.kevinqiu.lotterysystem.dao.dataobject.*;
import com.kevinqiu.lotterysystem.dao.mapper.*;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private WinningRecordMapper winningRecordMapper;
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    private static final String WINNING_RECORDS_PREFIX = "WINNING_RECORDS_";
    private static final Long WINNING_RECORDS_TIME_OUT = 60 * 60 * 24 * 2L;

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
//            throw new ServiceException(ServiceErrorCodeConstants.DRAW_PRIZE_PARAM_IS_NULL);
            return false;
        }

        // 验证活动和奖品是否存在
        ActivityDO activityDO = activityMapper.selectById(param.getActivityId());
        // 之所以可以使用 ActivityPrizeDO 判断活动奖品状态，是因为在保存活动信息时做了本地事务，保证了活动与活动关联奖品的一致性
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper
                .selectByActivityAndPrizeId(param.getActivityId(), param.getPrizeId());
        if (null == activityDO || null == activityPrizeDO) {
//            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_OR_PRIZE_IS_NULL);
            return false;
        }


        // 验证活动是否有效
        if (!activityDO.getStatus().equalsIgnoreCase(ActivityStatusEnum.RUNNING.name())) {
//            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_IS_COMPLETED);
            return false;
        }


        // 验证奖品是否有效
        if (!activityPrizeDO.getStatus().equalsIgnoreCase(ActivityPrizeStatusEnum.INIT.name())) {
//            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_IS_COMPLETED);
            return false;
        }

        // 验证奖品的数量是否等于中奖人数
        if (param.getWinnerList().size() != activityPrizeDO.getPrizeAmount()) {
//            throw new ServiceException(ServiceErrorCodeConstants.WINNER_PRIZE_AMOUNT_ERROR);
            return false;
        }

        return true;

    }

    @Override
    public void deleteWinnerRecords(Long activityId, Long prizeId) {

        // 当传递的奖品 Id 为空时，则默认把传来的 activityId 的整个活动相关的中奖记录库表+缓存都删除

        if (null == activityId) {
            log.warn("要删除的中奖记录的活动 Id 为空！");
            return;
        }

        // 直接删除记录
        winningRecordMapper.deleteByActivityIdAndPrizeId(activityId, prizeId);

        if (null != prizeId) {
            // 删除活动下奖品缓存
            deleteCacheWinningRecords(makeCacheWinningRecordId(activityId, prizeId));
        }

        // 活动的状态一定不是 COMPLETED ，所以必须要删除活动缓存
        deleteCacheWinningRecords(String.valueOf(prizeId));

    }

    @Override
    public List<WinningRecordDO> saveWinnerRecords(DrawPrizeParam param) {
        // 查询活动信息、关联奖品信息、中奖者人员信息
        ActivityDO activityDO = activityMapper.selectById(param.getActivityId());
        ActivityPrizeDO activityPrizeDO =
                activityPrizeMapper.selectByActivityAndPrizeId(param.getActivityId(), param.getPrizeId());
        PrizeInfoDO prizeInfoDO = prizeMapper.selectById(param.getPrizeId());
        List<UserDO> userBaseInfoDOS = userMapper.selectByIds(param.getWinnerList()
                .stream().map(DrawPrizeParam.Winner::getUserId).toList());

        List<WinningRecordDO> winningRecordDOList = userBaseInfoDOS.stream()
                .map(userBaseInfoDO -> {
                    WinningRecordDO winningRecordDO = new WinningRecordDO();

                    winningRecordDO.setActivityId(activityDO.getId());
                    winningRecordDO.setActivityName(activityDO.getActivityName());
                    winningRecordDO.setPrizeId(prizeInfoDO.getId());
                    winningRecordDO.setPrizeName(prizeInfoDO.getName());
                    winningRecordDO.setPrizeTier(activityPrizeDO.getPrizeTiers());
                    winningRecordDO.setWinnerId(userBaseInfoDO.getId());
                    winningRecordDO.setWinnerName(userBaseInfoDO.getUserName());
                    winningRecordDO.setWinnerEmail(userBaseInfoDO.getEmail());
                    winningRecordDO.setWinnerPhoneNumber(userBaseInfoDO.getPhoneNumber());
                    winningRecordDO.setWinningTime(param.getWinningTime());

                    return winningRecordDO;
                }).toList();

        winningRecordMapper.batchInsert(winningRecordDOList);

        // 缓存当前奖品中奖信息
        cacheWinningRecords(makeCacheWinningRecordId(param.getActivityId(), param.getPrizeId()),
                winningRecordDOList);

        // 缓存当前活动中奖信息，需要判断活动是否完成，存储的是这个活动的全部奖品的中奖信息
        if (activityDO.getStatus()
                .equalsIgnoreCase(ActivityStatusEnum.FINISHED.name())) {

            cacheWinningRecords(String.valueOf(param.getActivityId()),
                    winningRecordMapper.selectByActivityId(param.getActivityId()));

        }

        return winningRecordDOList;
    }

    @Override
    public List<WinningRecordDO> findWinnerRecords(ShowWinningRecordParam param) {

        return winningRecordMapper
                .selectByActivityIdAndPrizeId(param.getActivityId(), param.getPrizeId());

    }

    private void deleteCacheWinningRecords(String cacheId) {

        try {
            if (redisUtil.hasKey(WINNING_RECORDS_PREFIX + cacheId)) {
                redisUtil.del(WINNING_RECORDS_PREFIX + cacheId);
            }
        } catch (Exception e) {
            log.error("winningRecords 缓存删除失败，cacheId: {}", WINNING_RECORDS_PREFIX + cacheId);
        }

    }


    private void cacheWinningRecords(String cacheId, List<WinningRecordDO> winningRecordDOList) {
        try {

            redisUtil.set(WINNING_RECORDS_PREFIX + cacheId,
                    JacksonUtil.writeValueAsString(winningRecordDOList), WINNING_RECORDS_TIME_OUT);
        } catch (Exception e) {
            log.error("winningRecordDOList 缓存失败，winningRecordDOList: {}, e",
                    JacksonUtil.writeValueAsString(winningRecordDOList), e);
        }
    }

    private String makeCacheWinningRecordId(Long activityId, Long prizeId) {
        return activityId + "_" + prizeId;
    }

}
