package com.kevinqiu.lotterysystem.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityUserStatusEnum {

    INIT(1, "活动人员已初始化"),
    COMPLETED(2, "活动人员已抽奖");

    private final Integer code;
    private final String message;

    public static ActivityUserStatusEnum forName(String name) {
        for (ActivityUserStatusEnum statusEnum : ActivityUserStatusEnum.values()) {
            if (statusEnum.name().equalsIgnoreCase(name)) {
                return statusEnum;
            }
        }
        return null;
    }

}
