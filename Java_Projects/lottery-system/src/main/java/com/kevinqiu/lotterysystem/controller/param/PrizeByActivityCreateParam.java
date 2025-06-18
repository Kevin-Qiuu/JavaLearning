package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PrizeByActivityCreateParam implements Serializable {

    /**
     * 活动关联的奖品 id
     */
    @NotNull(message = "奖品 Id 为空！")
    private Long prizeId;

    /**
     * 活动关联的这个奖品的数量
     */
    @NotNull(message = "奖品数量为空！")
    private Long prizeAmount;

    /**
     * 活动关联的这个奖品的等级
     */
    @NotBlank(message = "奖品等级为空！")
    private String prizeTiers;

}
