package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.RedisUtil;
import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.controller.param.PrizeByActivityCreateParam;
import com.kevinqiu.lotterysystem.controller.param.UserByActivityCreateParam;
import com.kevinqiu.lotterysystem.dao.dataobject.ActivityDO;
import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.kevinqiu.lotterysystem.dao.dataobject.ActivityUserDO;
import com.kevinqiu.lotterysystem.dao.dataobject.PrizeInfoDO;
import com.kevinqiu.lotterysystem.dao.mapper.*;
import com.kevinqiu.lotterysystem.service.ActivityService;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityDetailDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityListPageDTO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityUserStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {


    private static final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);
    /**
     * 活动缓存前缀，为了区分其他业务
     */
    private final String ACTIVITY_PREFIX = "ACTIVITY_";
    /**
     * 活动缓存过期时间
     */
    private final Long ACTIVITY_TIME_OUT = 60 * 60 * 24 * 3L;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;
    @Autowired
    private ActivityUserMapper activityUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    public ActivityServiceImpl(UserMapper userMapper, PrizeMapper prizeMapper) {
        this.userMapper = userMapper;
        this.prizeMapper = prizeMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityCreateDTO activityCreate(ActivityCreateParam param) {

        // 校验 ActivityCreateParam
        checkActivityCreateParam(param);

        // 保存活动信息
        ActivityDO activityDO = new ActivityDO();
        activityDO.setActivityName(param.getActivityName());
        activityDO.setDescription(param.getDescription());
        activityDO.setStatus(ActivityStatusEnum.RUNNING.name());
        activityMapper.createActivity(activityDO);

        // 保存活动关联奖品信息
        List<ActivityPrizeDO> activityPrizeDOList = param.getActivityPrizeList()
                .stream()
                .map(activityPrize -> {
                    ActivityPrizeDO activityPrizeDO = new ActivityPrizeDO();
                    activityPrizeDO.setActivityId(activityDO.getId());
                    activityPrizeDO.setPrizeId(activityPrize.getPrizeId());
                    activityPrizeDO.setPrizeAmount(activityPrize.getPrizeAmount());
                    activityPrizeDO.setPrizeTiers(activityPrize.getPrizeTiers());
                    activityPrizeDO.setStatus(ActivityPrizeStatusEnum.INIT.name());
                    return activityPrizeDO;
                }).toList();
        activityPrizeMapper.batchCreateActivityPrize(activityPrizeDOList);

        // 保存活动关联人员信息
        List<ActivityUserDO> activityUserDOList = param.getActivityUserList()
                .stream()
                .map(activityUser -> {
                    ActivityUserDO activityUserDO = new ActivityUserDO();
                    activityUserDO.setActivityId(activityDO.getId());
                    activityUserDO.setUserId(activityUser.getUserId());
                    activityUserDO.setUserName(activityUser.getUserName());
                    activityUserDO.setStatus(ActivityUserStatusEnum.INIT.name());
                    return activityUserDO;
                }).toList();
        activityUserMapper.batchCreateActivityUser(activityUserDOList);

        // 整合完整的活动信息，存放 redis
        // activityId: ActivityDetailDTO:活动+奖品+人员

        // 先从数据库中获取 PrizeDO 的信息
        List<Long> prizeIdList = param.getActivityPrizeList()
                .stream()
                .map(PrizeByActivityCreateParam::getPrizeId)
                .toList();
        List<PrizeInfoDO> prizeInfoDOList = prizeMapper.selectByIds(prizeIdList);

        // 构造存储 Redis 的 ActivityDetailDTO 数据
        ActivityDetailDTO activityDetailDTO =
                convertToActivityDetailDTO(activityDO, activityPrizeDOList, prizeInfoDOList, activityUserDOList);

        // 存储到 Redis 中
        cacheActivity(activityDetailDTO);

        // 构造返回的 DTO 数据
        return new ActivityCreateDTO(activityDO.getId());
    }

    @Override
    public ActivityListPageDTO findActivityList(PageParam param) {
        Integer count = activityMapper.count();
        List<ActivityDO> activityPageList = activityMapper
                .selectActivityPageList(param.offset(), param.getPageSize());

        ActivityListPageDTO activityListPageDTO = new ActivityListPageDTO();
        activityListPageDTO.setTotal(count);
        activityListPageDTO.setAcivityInfoList(activityPageList
                .stream()
                .map(activityDO -> {
                    ActivityListPageDTO.ActivityInfo activityInfo = new ActivityListPageDTO.ActivityInfo();
                    activityInfo.setActivityId(activityDO.getId());
                    activityInfo.setActivityName(activityDO.getActivityName());
                    activityInfo.setDescription(activityDO.getDescription());
                    activityInfo.setStatusEnum(ActivityStatusEnum.forName(activityDO.getStatus()));
                    return activityInfo;
                }).toList());

        return activityListPageDTO;
    }

    @Override
    public ActivityDetailDTO findActivityDetail(Long activityId) {

        // 从 Redis 缓存中读取 ActivityDetail
        ActivityDetailDTO cacheActivity = getCacheActivity(activityId);
        if (null != cacheActivity) {
            return cacheActivity;
        }

        // Redis 缓存未命中，查询数据库
        ActivityDetailDTO activityDetailDTO = getActivityDetailDTOFromDO(activityId);
        // 存入 Redis 缓存中
        cacheActivity(activityDetailDTO);
        return activityDetailDTO;
    }

    private ActivityDetailDTO getCacheActivity(Long activityId) {

        ActivityDetailDTO activityDetailDTO = null;

        try {
            String activityDetailStr = redisUtil.get(ACTIVITY_PREFIX + activityId);
            activityDetailDTO = JacksonUtil.readValue(activityDetailStr, ActivityDetailDTO.class);
        } catch (Exception e) {
            log.error("获取缓存活动异常，ActivityId={}, e", activityId, e);
            return null;
        }

        return activityDetailDTO;
    }

    @Override
    public void cacheActivity(Long activityId) {
        if (null == activityId) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_ID_IS_NULL);
        }

        ActivityDO activityDO = activityMapper.selectById(activityId);
        if (null == activityDO) {
            throw new ServiceException(ServiceErrorCodeConstants.CACHE_ACTIVITY_INFO_IS_NULL);
        }

        cacheActivity(getActivityDetailDTOFromDO(activityId));
    }

    private ActivityDetailDTO getActivityDetailDTOFromDO(Long activityId) {
        ActivityDetailDTO activityDetailDTO = new ActivityDetailDTO();

        ActivityDO activityDO = activityMapper.selectById(activityId);
        List<ActivityUserDO> activityUserDOList = activityUserMapper.selectByActivityId(activityId);
        List<ActivityPrizeDO> activityPrizeDOList = activityPrizeMapper.selectByActivityId(activityId);
        List<PrizeInfoDO> prizeInfoDOList = prizeMapper.selectByIds(activityPrizeDOList.stream().map(ActivityPrizeDO::getPrizeId).toList());

        // 处理活动信息
        activityDetailDTO.setActivityId(activityDO.getId());
        activityDetailDTO.setActivityName(activityDO.getActivityName());
        activityDetailDTO.setDesc(activityDO.getDescription());
        activityDetailDTO.setStatus(ActivityStatusEnum.forName(activityDO.getStatus()));

        // 处理活动奖品信息
        activityDetailDTO.setPrizeDTOList(activityPrizeDOList.stream()
                .map(activityPrizeDO -> {
                    ActivityDetailDTO.PrizeDTO prizeDTO = new ActivityDetailDTO.PrizeDTO();
                    prizeInfoDOList.stream()
                            .filter(prizeInfoDO -> activityPrizeDO.getPrizeId().equals(prizeInfoDO.getId()))
                            .findFirst()
                            .ifPresent(prizeInfoDO -> {
                                prizeDTO.setPrizeId(prizeInfoDO.getId());
                                prizeDTO.setName(prizeInfoDO.getName());
                                prizeDTO.setImageUrl(prizeInfoDO.getImageUrl());
                                prizeDTO.setPrice(prizeInfoDO.getPrice());
                                prizeDTO.setDescription(prizeInfoDO.getDescription());
                            });
                    prizeDTO.setTiers(ActivityPrizeTiersEnum.forName(activityPrizeDO.getPrizeTiers()));
                    prizeDTO.setPrizeAmount(activityPrizeDO.getPrizeAmount());
                    prizeDTO.setStatus(ActivityPrizeStatusEnum.forName(activityPrizeDO.getStatus()));
                    return prizeDTO;
                }).toList());

        // 处理活动人员信息
        activityDetailDTO.setUserDTOList(activityUserDOList.stream()
                .map(activityUserDO -> {
                    ActivityDetailDTO.UserDTO userDTO = new ActivityDetailDTO.UserDTO();
                    userDTO.setUserId(activityUserDO.getUserId());
                    userDTO.setUserName(activityUserDO.getUserName());
                    userDTO.setStatus(ActivityUserStatusEnum.forName(activityUserDO.getStatus()));
                    return userDTO;
                }).toList());

        return activityDetailDTO;
    }

    public void cacheActivity(ActivityDetailDTO activityDetailDTO) {

        if (null == activityDetailDTO) {
            throw new ServiceException(ServiceErrorCodeConstants.CACHE_ACTIVITY_INFO_IS_NULL);
        }

        try{
            redisUtil.set(ACTIVITY_PREFIX + activityDetailDTO.getActivityId()
                    , JacksonUtil.writeValueAsString(activityDetailDTO), ACTIVITY_TIME_OUT);
        } catch (Exception e) {
            log.error("缓存活动异常，ActivityDetailDTO={}, e:"
                    , JacksonUtil.writeValueAsString(activityDetailDTO), e);
        }

    }

    private ActivityDetailDTO convertToActivityDetailDTO(ActivityDO activityDO,
                                                         List<ActivityPrizeDO> activityPrizeDOList,
                                                         List<PrizeInfoDO> prizeInfoDOList,
                                                         List<ActivityUserDO> activityUserDOList) {

        ActivityDetailDTO activityDetailDTO = new ActivityDetailDTO();

        // 设置 ActivityDetailDTO 中的活动信息
        activityDetailDTO.setActivityId(activityDO.getId());
        activityDetailDTO.setActivityName(activityDO.getActivityName());
        activityDetailDTO.setDesc(activityDO.getDescription());
        activityDetailDTO.setStatus(ActivityStatusEnum.forName(activityDO.getStatus()));

        // 设置 ActivityDetailDTO 中的奖品信息
        // 多看看这段代码（多层流，也就是多层 for ）
        List<ActivityDetailDTO.PrizeDTO> prizeDTOList = activityPrizeDOList.stream()
                .map(activityPrizeDO -> {
                    ActivityDetailDTO.PrizeDTO prizeDTO = new ActivityDetailDTO.PrizeDTO();
                    prizeInfoDOList.stream()
                            .filter(prizeInfoDO -> prizeInfoDO.getId().equals(activityPrizeDO.getPrizeId()))
                            .findFirst()
                            .ifPresent(prizeInfoDO -> {
                                prizeDTO.setPrizeId(prizeInfoDO.getId());
                                prizeDTO.setName(prizeInfoDO.getName());
                                prizeDTO.setPrice(prizeInfoDO.getPrice());
                                prizeDTO.setImageUrl(prizeInfoDO.getImageUrl());
                                prizeDTO.setPrizeAmount(activityPrizeDO.getPrizeAmount());
                                prizeDTO.setTiers(ActivityPrizeTiersEnum.forName(activityPrizeDO.getPrizeTiers()));
                                prizeDTO.setDescription(activityDO.getDescription());
                                prizeDTO.setStatus(ActivityPrizeStatusEnum.forName(activityPrizeDO.getStatus()));
                            });
                    return prizeDTO;
                }).toList();
        activityDetailDTO.setPrizeDTOList(prizeDTOList);

        // 设置 ActivityDetailDTO 中的人员信息列表
        List<ActivityDetailDTO.UserDTO> userDTOList = activityUserDOList.stream()
                .map(activityUserDO -> {
                    ActivityDetailDTO.UserDTO userDTO = new ActivityDetailDTO.UserDTO();
                    userDTO.setUserId(activityUserDO.getUserId());
                    userDTO.setUserName(activityUserDO.getUserName());
                    userDTO.setStatus(ActivityUserStatusEnum.forName(activityUserDO.getStatus()));
                    return userDTO;
                }).toList();
        activityDetailDTO.setUserDTOList(userDTOList);

        return activityDetailDTO;

    }

    private void checkActivityCreateParam(ActivityCreateParam param) {
        if (null == param) {
            throw new ServiceException(ServiceErrorCodeConstants.CREATE_ACTIVITY_INFO_IS_EMPTY);
        }

        // 判断人员 id 在人员表中是否一一存在
        List<Long> userIdList = param.getActivityUserList()
                .stream()
                .map(UserByActivityCreateParam::getUserId)
                .toList();
        List<Long> userIdListDB = userMapper.selectByIds(userIdList);
        if (null == userIdListDB) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_INFO_ERROR);
        }
        userIdList.forEach(userId -> {
            if (!userIdListDB.contains(userId)) {
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_INFO_ERROR);
            }
        });

        // 判断奖品 id 在奖品表中是否一一存在
        List<Long> prizeIds = param.getActivityPrizeList()
                .stream()
                .map(PrizeByActivityCreateParam::getPrizeId)
                .toList();
        List<PrizeInfoDO> prizeInfoDOList = prizeMapper.selectByIds(prizeIds);
        List<Long> prizeIdsDB = prizeInfoDOList.stream().map(PrizeInfoDO::getId).toList();
        prizeIds.forEach(prizeId -> {
            if (!prizeIdsDB.contains(prizeId)) {
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_INFO_ERROR);
            }
        });

        // 校验参与活动的人数是否大于等于奖品的数量
        long userCount = param.getActivityUserList().size();
        long prizeCount = param.getActivityPrizeList()
                .stream()
                .mapToLong(PrizeByActivityCreateParam::getPrizeAmount)
                .sum();
        if (userCount < prizeCount) {
            throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_COUNT_LESS_THAN_PRIZE_COUNT);
        }

        // 校验活动奖品的等奖有效性
        param.getActivityPrizeList()
                .stream()
                .map(PrizeByActivityCreateParam::getPrizeTiers)
                .forEach(prizeTiers -> {
                    if (null == ActivityPrizeTiersEnum.forName(prizeTiers)) {
                        throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_TIERS_ERROR);
                    }
                });

    }

}
