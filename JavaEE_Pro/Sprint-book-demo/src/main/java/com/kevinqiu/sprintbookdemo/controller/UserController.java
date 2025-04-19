package com.kevinqiu.sprintbookdemo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login")
    public Integer login(String userName, String password, HttpSession session){

        if(!StringUtils.hasLength((userName)) || !StringUtils.hasLength((password))){
            return -1;
        }

        if (!"KevinQiu".equals(userName) || !"KevinQiu".equals(password)) {
            return 0;
        }

        // 校验成功，返回 1，设置用户的session
        session.setAttribute("userName", "KevinQiu");
        session.setAttribute("password", "KevinQiu");
        return 1;
    }
}
