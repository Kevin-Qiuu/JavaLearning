package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShowWinningRecordParam implements Serializable {

    /**
     * 活动 Id
     */
    @NotNull(message = "活动 Id 不可为空！")
    private Long activityId;

    /**
     * 奖品 Id，当奖品 Id 为空是默认查整个活动的中奖记录
     */
    private Long prizeId;

}
