package com.kevinqiu.lotterysystem.controller.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResult implements Serializable {
    /**
     * 用户身份
     */
    private String identity;

    /**
     * 用户的 JWT
     */
    private String token;
}
