package com.kevinqiu.lotterysystem.controller.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserLoginParam implements Serializable {
    /**
     * 强制以某身份登录
     * @see com.kevinqiu.lotterysystem.service.enums.UserIdentityEnum
     */
    private String mandatoryIdentity;
}
