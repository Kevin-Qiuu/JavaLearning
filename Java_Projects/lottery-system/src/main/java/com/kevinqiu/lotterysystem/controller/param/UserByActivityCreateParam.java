package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserByActivityCreateParam implements Serializable {

    /**
     * 参与活动的用户Id
     */
    @NotNull(message = "人员 ID 为空！")
    private Long userId;

    /**
     * 参与活动的用户名，避免了多次查数据库
     */
    @NotBlank(message = "人员名称为空！")
    private String userName;

}
