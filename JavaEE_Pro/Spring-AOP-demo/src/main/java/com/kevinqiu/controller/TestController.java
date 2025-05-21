package com.kevinqiu.controller;

import com.kevinqiu.aspect.MyPointCut;
import com.kevinqiu.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/t1")
    public UserInfo t1(Integer id, String username) throws InterruptedException {
        log.info("TestController.t1 方法开始执行");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUsername(username);
        return userInfo;
    }

    @RequestMapping("/t2")
    public UserInfo t2(Integer id, String username) throws InterruptedException {
        log.info("TestController.t2 方法开始执行");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUsername(username);
        return userInfo;
    }
}
