package com.kevinqiu.controller;

import com.kevinqiu.aspect.MyPointCut;
import com.kevinqiu.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @MyPointCut
    @RequestMapping("/u1")
    public UserInfo u1(Integer id, String username){
        log.info("UserController.u1 方法开始执行");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUsername(username);
        return userInfo;
    }

    @RequestMapping("/u2")
    public UserInfo u2(Integer id, String username){
        log.info("UserController.u2 方法开始执行");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUsername(username);
        return userInfo;
    }

}
