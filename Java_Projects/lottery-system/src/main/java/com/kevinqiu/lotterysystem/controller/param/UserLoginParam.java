package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "身份信息为空！")
    private String mandatoryIdentity;
}
