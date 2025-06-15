package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPasswordLoginParam extends UserLoginParam{

    /**
     * 用户登录名（邮箱或者手机）
     */
    @NotBlank(message = "登录名为空！")
    private String loginName;

    /**
     * 用户登录密码
     */
    private String password;

}
