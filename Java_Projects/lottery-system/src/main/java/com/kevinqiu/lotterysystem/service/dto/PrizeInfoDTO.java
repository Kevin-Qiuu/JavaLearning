package com.kevinqiu.lotterysystem.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrizeInfoDTO {
    /**
         * 奖品id
         */
        private Long prizeId;

        /**
         * 名称
         */
        private String prizeName;

        /**
         * 描述
         */
        private String description;

        /**
         * 价值
         */
        private BigDecimal price;

        /**
         * 奖品图
         */
        private String imageUrl;
}
