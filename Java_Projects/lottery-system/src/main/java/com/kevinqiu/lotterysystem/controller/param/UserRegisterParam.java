package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterParam implements Serializable {
     /**
     * 用户名
     */
    @NotBlank(message = "用户注册姓名为空！")
    private String name;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "用户注册邮箱为空！")
    private String mail;

    /**
     * 电话号码
     */
    @NotBlank(message = "用户注册电话为空！")
    private String phoneNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户身份
     */
    @NotBlank(message = "用户注册身份为空！")
    private String identity;

}
