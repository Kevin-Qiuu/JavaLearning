package com.kevinqiu.lotterysystem.dao.dataobject;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseDO{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 电话号码
     */
    private Encrypt phoneNumber;

    /**
     * 密码
     */
    private Encrypt password;

    /**
     * 用户身份
     */
    private String identity;
}
