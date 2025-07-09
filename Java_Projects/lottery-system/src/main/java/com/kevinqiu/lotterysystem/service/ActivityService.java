package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.ActivityCreateParam;
import com.kevinqiu.lotterysystem.controller.param.PageParam;
import com.kevinqiu.lotterysystem.service.dto.ActivityCreateDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityDetailDTO;
import com.kevinqiu.lotterysystem.service.dto.ActivityListPageDTO;

public interface ActivityService {

    /**
     * 活动创建
     *
     * @param param
     * @return
     */
    ActivityCreateDTO activityCreate(ActivityCreateParam param);

    /**
     * 翻页查询活动（摘要）列表
     *
     * @param param
     * @return
     */
    ActivityListPageDTO findActivityList(PageParam param);

    /**
     * 查询某一活动的详细信息
     *
     * @param activityId
     * @return
     */
    ActivityDetailDTO findActivityDetail(Long activityId);

    /**
     * 根据活动 Id 查询对应详细信息，并缓存
     *
     * @param activityId
     */
    void cacheActivity(Long activityId);
}
