package com.kevinqiu.lotterysystem.controller.result;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserBaseInfoResult implements Serializable {

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
