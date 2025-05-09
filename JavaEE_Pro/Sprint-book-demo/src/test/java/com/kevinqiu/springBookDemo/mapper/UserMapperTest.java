package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void selectUserByUsername() {
        UserInfo userInfo = userMapper.selectUserByUsername("kevinqiu");
        System.out.println(userInfo);
    }
}