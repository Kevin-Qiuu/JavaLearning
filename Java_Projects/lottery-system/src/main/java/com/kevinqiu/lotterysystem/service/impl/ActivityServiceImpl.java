package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.service.ActivityService;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityCreateDTO activityCreate(ActivityCreateParam param) {
        // 校验 ActivityCreateParam

        // 保存活动信息

        // 保存活动关联奖品信息

        // 保存活动关联人员信息

        // 整合完整的活动信息，存放 redis
        // activityId: ActivityDetailDTO:活动+奖品+人员

        // 构造返回的 DTO 数据


    }
}
