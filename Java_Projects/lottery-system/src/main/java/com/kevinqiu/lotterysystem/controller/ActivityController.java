package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.controller.result.ActivityCreateResult;
import com.kevinqiu.lotterysystem.controller.result.ActivityDetailResult;
import com.kevinqiu.lotterysystem.controller.result.ActivityListPageResult;
import com.kevinqiu.lotterysystem.service.ActivityService;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityDetailDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityListPageDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/activity/create")
    public CommonResult<ActivityCreateResult> activityCreate(@Validated @RequestBody
                                                             ActivityCreateParam param) {
        log.info("activityCreate -> ActivityCreateParam: {}", JacksonUtil.writeValueAsString(param));
        return CommonResult.success(convertToActivityCreateDTO(activityService.activityCreate(param)));
    }

    @RequestMapping("/activity/find-page")
    public CommonResult<ActivityListPageResult> findActivityList(@Validated PageParam param) {
        if (null == param) {
            throw new ControllerException(ControllerErrorCodeConstants.ACTIVITY_PAGE_PARAM_IS_NULL);
        }

        ActivityListPageDTO activityListPageDTO = activityService.findActivityList(param);

        return CommonResult.success(convertTOActivityListPageResult(activityListPageDTO));

    }

    @RequestMapping("/activity-detail/find")
    public CommonResult<ActivityDetailResult> findActivityDetail(Long activityId) {
        if (null == activityId) {
            throw new ControllerException(ControllerErrorCodeConstants.ACTIVITY_ID_IS_NULL);
        }

        ActivityDetailDTO activityDetailDTO = activityService.findActivityDetail(activityId);
        return CommonResult.success(converToActivityDetailResult(activityDetailDTO));
    }

    private ActivityDetailResult converToActivityDetailResult(ActivityDetailDTO activityDetailDTO) {

        if (null == activityDetailDTO) {
            throw new ControllerException(ControllerErrorCodeConstants.ACTIVITY_DETAIL_IS_NULL);
        }

        ActivityDetailResult activityDetailResult = new ActivityDetailResult();

        // 活动信息的设置
        activityDetailResult.setActivityId(activityDetailDTO.getActivityId());
        activityDetailResult.setActivityName(activityDetailDTO.getActivityName());
        activityDetailResult.setDescription(activityDetailDTO.getDesc());
        activityDetailResult.setValid(activityDetailDTO.valid());

        // 活动奖品信息的设置
        activityDetailResult.setPrizes(
                activityDetailDTO.getPrizeDTOList().stream()
                        // 设置抽奖顺序，一等奖，二等奖，三等奖
                        // todo 理解 sorted 这行代码
                        .sorted(Comparator.comparingInt(prizeDO -> prizeDO.getTiers().getCode()))
                        .map(prizeDTO -> {
                            ActivityDetailResult.Prize prize = new ActivityDetailResult.Prize();
                            prize.setPrizeId(prizeDTO.getPrizeId());
                            prize.setName(prizeDTO.getName());
                            prize.setPrizeAmount(prizeDTO.getPrizeAmount());
                            prize.setPrice(prizeDTO.getPrice());
                            prize.setImageUrl(prizeDTO.getImageUrl());
                            prize.setDescription(prizeDTO.getDescription());
                            prize.setPrizeTierName(prizeDTO.getTiers().getMessage());
                            prize.setValid(prizeDTO.valid());
                            return prize;
                        }).toList()
        );

        // 活动人员信息的设置
        activityDetailResult.setUsers(
                activityDetailDTO.getUserDTOList().stream()
                        .map(userDTO -> {
                            ActivityDetailResult.User user = new ActivityDetailResult.User();
                            user.setUserId(userDTO.getUserId());
                            user.setUserName(userDTO.getUserName());
                            user.setValid(userDTO.valid());
                            return user;
                        }).toList()
        );

        return activityDetailResult;
    }


    private ActivityListPageResult convertTOActivityListPageResult(ActivityListPageDTO activityListPageDTO) {
        if (null == activityListPageDTO) {
            throw new ControllerException(ControllerErrorCodeConstants.ACTIVITY_PAGE_LIST_IS_NULL);
        }

        ActivityListPageResult activityListPageResult = new ActivityListPageResult();
        activityListPageResult.setTotal(activityListPageDTO.getTotal());
        activityListPageResult.setAcivityInfoList(activityListPageDTO
                .getAcivityInfoList()
                .stream()
                .map(activityInfoDTO -> {
                    ActivityListPageResult.ActivityInfo activityInfo = new ActivityListPageResult.ActivityInfo();
                    activityInfo.setActivityId(activityInfoDTO.getActivityId());
                    activityInfo.setDescription(activityInfoDTO.getDescription());
                    activityInfo.setActivityName(activityInfoDTO.getActivityName());
                    activityInfo.setValid(activityInfoDTO.valid());
                    return activityInfo;
                }).toList());

        return activityListPageResult;
    }

    private ActivityCreateResult convertToActivityCreateDTO(ActivityCreateDTO activityCreateDTO) {
        if (null == activityCreateDTO) {
            throw new ControllerException(ControllerErrorCodeConstants.ACTIVITY_CREATE_FAILED);
        }
        ActivityCreateResult activityCreateResult = new ActivityCreateResult();
        activityCreateResult.setActivityId(activityCreateDTO.getActivityId());
        return activityCreateResult;
    }

}
