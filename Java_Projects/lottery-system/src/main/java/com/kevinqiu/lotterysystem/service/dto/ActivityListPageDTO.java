package com.kevinqiu.lotterysystem.service.dto;

import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ActivityListPageDTO {

    /**
     * 奖品总数
     */
    private Integer total;

    /**
     * 当前列表
     */
    private List<ActivityInfo> acivityInfoList;

    @Data
    @NoArgsConstructor
    public static class ActivityInfo {
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
        private ActivityStatusEnum statusEnum;

        public Boolean valid() {return statusEnum.equals(ActivityStatusEnum.RUNNING);}

    }
}
