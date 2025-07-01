package com.kevinqiu.lotterysystem.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kevinqiu.lotterysystem.service.dto.PrizeInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeInfoListResult implements Serializable {

//    @JsonProperty("abc") // 这个注解用于设置 json 的 key 名称
    private List<PrizeInfo> prizeInfoList;

    @Data
    public static class PrizeInfo {

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
