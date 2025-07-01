package com.kevinqiu.lotterysystem.service.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ActivityStatusEnum {

    RUNNING(1, "活动进行中"),
    FINISHED(2, "活动已结束");

    private final Integer code;
    private final String message;

    public static ActivityStatusEnum forName(String name) {
        for (ActivityStatusEnum activityStatusEnum : ActivityStatusEnum.values()) {
            if (activityStatusEnum.name().equalsIgnoreCase(name)) {
                return activityStatusEnum;
            }
        }
        return null;
    }

}
