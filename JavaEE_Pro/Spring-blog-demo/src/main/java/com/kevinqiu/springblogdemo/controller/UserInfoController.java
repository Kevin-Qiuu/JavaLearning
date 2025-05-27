package com.kevinqiu.springblogdemo.controller;

import com.kevinqiu.springblogdemo.pojo.request.UserLoginRequest;
import com.kevinqiu.springblogdemo.pojo.response.UserLoginResponse;
import com.kevinqiu.springblogdemo.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping("/login")
    public UserLoginResponse login(@Validated @RequestBody UserLoginRequest userLoginRequest){
        log.info("用户登录，username: {}", userLoginRequest.getUsername());
        return userService.login(userLoginRequest);
    }

}
