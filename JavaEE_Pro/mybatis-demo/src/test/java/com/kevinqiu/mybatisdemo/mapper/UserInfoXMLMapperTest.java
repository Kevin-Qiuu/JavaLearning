package com.kevinqiu.mybatisdemo.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoXMLMapperTest {
    @Autowired
    UserInfoXMLMapper userInfoXMLMapper;

    @Test
    void selectAllUser() {
        userInfoXMLMapper.selectAllUser().stream().forEach(System.out::println);
    }

    @Test
    void selectUserById() {
        UserInfo userInfo = userInfoXMLMapper.selectUserById(1);
        System.out.println(userInfo);
    }

    @Test
    void insertUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("kevinqiu202505");
        userInfo.setPassword("kevinqiu202505");
        userInfo.setAge(18);
//        userInfo.setGender(1);
//        userInfo.setPhone("123456");
        Integer ret = userInfoXMLMapper.insertUser(userInfo);
        System.out.println(userInfo);
        System.out.println("影响的行数：" + ret);
    }

    @Test
    void deleteUser() {
        Integer ret = userInfoXMLMapper.deleteUserById(10);
        System.out.println("影响的行数：" + ret);
    }

    @Test
    void updateUserById() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(3);
        userInfo.setUsername("kevinqiu2025");
        userInfo.setPassword("kevinqiu2025");
        userInfo.setAge(18);
        userInfo.setGender(1);
        userInfo.setPhone("123456");
        Integer ret = userInfoXMLMapper.updateUserById(userInfo);
        System.out.println("影响的行数：" + ret);
    }


    @Test
    void selectUserByUsername() {
        userInfoXMLMapper.selectUserByUsername("kevinqiu2025").stream().forEach(System.out::println);
    }

    @Test
    void selectUserByLikeUsername() {
        // 使用 ${} 会存在 SQL 注入的隐患
//        userInfoXMLMapper.selectUserByLikeUsername("%' or '1'='1").stream().forEach(System.out::println);
        userInfoXMLMapper.selectUserByLikeUsername("qiu").stream().forEach(System.out::println);
    }

    @Test
    void insertUserByCondition() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("kevinqiu2025");
        userInfo.setPassword("kevinqiu2025");
        userInfo.setAge(18);
//        userInfo.setGender(1);
//        userInfo.setPhone("123456");
        Integer ret = userInfoXMLMapper.insertUserByCondition(userInfo);
        System.out.println("影响的行数：" + ret);


    }

    @Test
    void selectUserByCondition() {
        UserInfo userInfo = new UserInfo();
//        userInfo.setUsername("kevinqiu2025");
//        userInfo.setPassword("kevinqiu2025");
//        userInfo.setAge(18);
//        userInfo.setGender(1);
//        userInfo.setPhone("123456");
        userInfoXMLMapper.selectUserByCondition(userInfo).stream().forEach(System.out::println);
    }

    @Test
    void updateUserByCondition() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(3);
        userInfo.setUsername("kevinqiu");
        userInfo.setPassword("kevinqiu");
        Integer ret = userInfoXMLMapper.updateUserByCondition(userInfo);
        System.out.println("影响的行数：" + ret);

//        userInfo.setAge(18);
//        userInfo.setGender(1);
//        userInfo.setPhone("123456");
    }

    @Test
    void deleteUsersByIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(20);
        ids.add(21);
        ids.add(22);
        Integer ret = userInfoXMLMapper.deleteUsersByIds(ids);
        System.out.println("影响的行数：" + ret);
    }
}