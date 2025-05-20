package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class UserInfoPlusMapperTest {
    @Autowired
    private UserInfoPlusMapper userMapper;

    @Test
    public void testSelectById(){
        System.out.println(userMapper.selectById(1));
    }

    @Test
    public void testSelectByIds(){
        userMapper.selectBatchIds(List.of(1,2,3)).stream().forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("KevinQ@foxmail.com");
        userInfo.setPassword("123456");
        userMapper.insert(userInfo);
    }

    @Test
    public void testDelete(){
        userMapper.deleteById(4);
    }


}
