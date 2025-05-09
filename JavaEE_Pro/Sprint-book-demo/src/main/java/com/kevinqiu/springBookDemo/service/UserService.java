package com.kevinqiu.springBookDemo.service;

import com.kevinqiu.springBookDemo.mapper.UserMapper;
import com.kevinqiu.springBookDemo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public UserInfo selectUserByUsername (String username) throws IllegalArgumentException{
        if (!StringUtils.hasLength(username)) {
            throw new IllegalArgumentException("Username is null!");
        }
        return userMapper.selectUserByUsername(username);
    }
}
