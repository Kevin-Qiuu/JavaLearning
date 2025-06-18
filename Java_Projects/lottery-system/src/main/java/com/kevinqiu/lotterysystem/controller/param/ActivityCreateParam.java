package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.List;

public class ActivityCreateParam implements Serializable {

    /**
     * 活动名称
     */
    @NotBlank(message = "活动名称为空！")
    private String activityName;

    /**
     * 活动描述
     */
    @NotBlank(message = "活动描述为空！")
    private String description;

    /**
     * 当前活动关联的奖品列表
     */
    @NotEmpty(message = "活动列表为空！")
    @Valid
    private List<PrizeByActivityCreateParam> activityPrizeList;

    /**
     * 当前活动关联的用户列表
     */
    @NotEmpty(message = "人员列表为空！")
    @Valid
    private List<UserByActivityCreateParam> activityUserList;

}
