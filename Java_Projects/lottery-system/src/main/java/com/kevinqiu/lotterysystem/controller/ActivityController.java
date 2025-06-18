package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.controller.result.ActivityCreateResult;
import com.kevinqiu.lotterysystem.service.ActivityService;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private ActivityCreateResult convertToActivityCreateDTO(ActivityCreateDTO activityCreateDTO) {
        if (null == activityCreateDTO) {
            throw new ControllerException(ControllerErrorCodeConstants.ACTIVITY_CREATE_FAILED);
        }
        ActivityCreateResult activityCreateResult = new ActivityCreateResult();
        activityCreateResult.setActivityId(activityCreateDTO.getActivityId());
        return activityCreateResult;
    }

}
