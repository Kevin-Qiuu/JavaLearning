package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;

public interface ActivityService {
    ActivityCreateDTO activityCreate(ActivityCreateParam param);
}
