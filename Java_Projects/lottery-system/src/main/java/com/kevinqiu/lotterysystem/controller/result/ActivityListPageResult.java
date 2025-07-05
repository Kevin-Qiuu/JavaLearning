package com.kevinqiu.lotterysystem.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListPageResult implements Serializable {

    /**
     * 奖品总数
     */
    private Integer total;

    /**
     * 当前列表
     */
    @JsonProperty("records")
    private List<ActivityInfo> acivityInfoList;

    @Data
    @NoArgsConstructor
    public static class ActivityInfo implements Serializable{
        /**
         * 活动id
         */
        private Long activityId;

        /**
         * 活动名称
         */
        private String activityName;

        /**
         * 活动描述
         */
        private String description;

        /**
         * 活动是否有效
         */
        private Boolean valid;

    }

}
