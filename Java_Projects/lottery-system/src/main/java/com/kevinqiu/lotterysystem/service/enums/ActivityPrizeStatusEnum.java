package com.kevinqiu.lotterysystem.service.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityPrizeStatusEnum {

    INIT(1, "活动奖品已初始化"),
    COMPLETED(2, "活动奖品已被抽取");

    private final Integer code;
    private final String message;

    public static ActivityPrizeStatusEnum forName(String name) {
        for (ActivityPrizeStatusEnum statusEnum : ActivityPrizeStatusEnum.values()) {
            if (statusEnum.name().equalsIgnoreCase(name)) {
                return statusEnum;
            }
        }
        return null;
    }


}
