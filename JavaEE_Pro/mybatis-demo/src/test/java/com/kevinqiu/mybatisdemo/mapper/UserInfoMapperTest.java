package com.kevinqiu.mybatisdemo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserInfoMapperTest {

    @Autowired // 注入依赖
    private UserInfoMapper userInfoMapper;

    @Test
    void queryAll() {
        System.out.println(userInfoMapper.queryAll());
    }

    @Test
    void queryById() {
        System.out.println(userInfoMapper.queryById(1));
    }

    @Test
    void insertUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("KevinQiu");
        userInfo.setAge(23);
        userInfo.setPassword("KevinQiu");
        userInfo.setGender(1);
        System.out.println("The effected lines: " + userInfoMapper.insertUser(userInfo));
        System.out.println("The id of the added line: " + userInfo.getId());
    }
}