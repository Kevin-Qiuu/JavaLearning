package com.kevinqiu.lotterysystem.service.dto;

import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class ConvertActivityStatusDTO {

    /**
     * 活动 Id
     */
    private Long activityId;

    /**
     * 活动转换的目标状态
     */
    private ActivityStatusEnum targetActivityStatus;

    /**
     * 活动关联的奖品 Id
     */
    private Long prizeId;

    /**
     * 活动关联的奖品转换的目标状态
     */
    private ActivityPrizeStatusEnum targetPrizeStatus;

    /**
     * 活动关联的人员 Id 列表
     */
    private List<Long> userIds;

    /**
     * 活动关联的人员转换的目标状态
     */
    private ActivityUserStatusEnum targetUserStatus;

}
