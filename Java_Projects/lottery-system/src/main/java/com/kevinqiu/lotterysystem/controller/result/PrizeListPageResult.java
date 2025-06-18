package com.kevinqiu.lotterysystem.controller.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeListPageResult implements Serializable {

    /**
     * 奖品的总数
     */
    private Integer total;

    private List<PrizeInfo> prizeInfoList;

    @Data
    public static class PrizeInfo implements Serializable{

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


}
