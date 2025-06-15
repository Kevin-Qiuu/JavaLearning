package com.kevinqiu.lotterysystem.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDTO {
    /**
     * 用户身份信息
     */
    private String identity;
    private String token;
}