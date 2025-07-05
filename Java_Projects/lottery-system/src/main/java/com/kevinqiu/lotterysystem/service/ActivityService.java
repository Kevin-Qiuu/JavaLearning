package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityDetailDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityListPageDTO;

public interface ActivityService {
    ActivityCreateDTO activityCreate(ActivityCreateParam param);

    ActivityListPageDTO findActivityList(PageParam param);

    ActivityDetailDTO findActivityDetail(Long activityId);
}
