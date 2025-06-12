package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMessageLoginParam extends UserLoginParam{

    /**
     * 电话号码
     */
    @NotBlank(message = "电话号码为空！")
    private String phoneNumber;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码为空！")
    private String verificationCode;

}
