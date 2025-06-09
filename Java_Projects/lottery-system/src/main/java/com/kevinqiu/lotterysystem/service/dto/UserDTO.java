package com.kevinqiu.lotterysystem.service.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    /**
     * 用户名
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户身份
     */
    private String identity;
}
