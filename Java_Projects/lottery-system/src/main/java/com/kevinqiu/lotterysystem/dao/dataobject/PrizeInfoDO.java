package com.kevinqiu.lotterysystem.dao.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PrizeInfoDO extends BaseDO{

    /**
     * 奖品名称
     */
    private String name;

    /**
     * 奖品描述
     */
    private String description;

    /**
     * 奖品价格
     */
    private BigDecimal price;

    /**
     * 奖品图像路径
     */
    private String imageUrl;

}
