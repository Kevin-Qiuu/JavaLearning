package com.kevinqiu.springBookDemo.controller;

import com.kevinqiu.springBookDemo.model.UserInfo;
import com.kevinqiu.springBookDemo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @RequestMapping("/login")
    public Integer login(String username, String password, HttpSession session){

        if(!StringUtils.hasLength((username)) || !StringUtils.hasLength((password))){
            return -1;
        }

        // 调用数据库校验用户名与密码是否正确
        UserInfo selectUserInfo = null;
        try{
            selectUserInfo = userService.selectUserByUsername(username);
        } catch (Exception e){
            logger.info(e.toString());
            return 0;
        }

        // 用户不存在，校验失败，返回 0
        if (selectUserInfo == null) {
            return 0;
        }

        // 用户存在，密码错误，校验失败，返回 0
        if (!selectUserInfo.getPassword().equals(password)) {
            return 0;
        }


        // 校验成功，返回 1，设置用户的session
        session.setAttribute("userId", selectUserInfo.getId());
//        session.setAttribute("password", password);
        return 1;
    }
}
