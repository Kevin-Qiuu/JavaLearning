package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.controller.result.UserRegisterResult;
import com.kevinqiu.lotterysystem.service.UserService;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;
import com.kevinqiu.lotterysystem.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {


    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping("/register")
    public CommonResult<UserRegisterResult> userRegister(@Validated @RequestBody UserRegisterParam param){
        log.info("userRegister->UserRegisterParam: {}", JacksonUtil.writeValueAsString(param));
        UserRegisterDTO userRegisterDTO = userService.register(param);
        return CommonResult.success(convertUserRegisterDTO(userRegisterDTO));
    }




    private UserRegisterResult convertUserRegisterDTO(UserRegisterDTO userRegisterDTO){
        UserRegisterResult userRegisterResult = new UserRegisterResult();
        userRegisterResult.setUserId(userRegisterDTO.getUserId());
        return userRegisterResult;
    }

}
