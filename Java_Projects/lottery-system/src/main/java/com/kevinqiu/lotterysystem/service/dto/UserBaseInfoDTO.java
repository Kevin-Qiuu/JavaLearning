package com.kevinqiu.lotterysystem.service.dto;

import lombok.Data;

@Data
public class UserBaseInfoDTO {
    /**
     * 用户 Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户身份
     */
    private String identity;
}
