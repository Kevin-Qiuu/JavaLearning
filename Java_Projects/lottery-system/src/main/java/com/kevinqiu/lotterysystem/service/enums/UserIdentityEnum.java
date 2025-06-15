package com.kevinqiu.lotterysystem.service.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserIdentityEnum {
    ADMIN("管理员"),
    NORMAL("普通用户");

    private final String identityMessage;

    public static String forName(String identityName){
        for (UserIdentityEnum userIdentityEnum : UserIdentityEnum.values()){
            if (userIdentityEnum.name().equalsIgnoreCase(identityName)){
                return userIdentityEnum.name();
            }
        }
        return null;
    }
}
