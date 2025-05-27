package com.kevinqiu.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kevinqiu.springblogdemo.common.utils.JwtUtils;
import com.kevinqiu.springblogdemo.mapper.UserInfoMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.UserInfo;
import com.kevinqiu.springblogdemo.pojo.request.UserLoginRequest;
import com.kevinqiu.springblogdemo.pojo.response.UserLoginResponse;
import com.kevinqiu.springblogdemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource(name = "userInfoMapper")
    private UserInfoMapper userInfoMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        if (!StringUtils.hasLength(userLoginRequest.getUsername())
                || !StringUtils.hasLength(userLoginRequest.getPassword())) {
            return null;
        }

        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername, userLoginRequest.getUsername()));

        if (!userInfo.getPassword().equals(userLoginRequest.getPassword())) {
            return null;
        }


        // 创建 Token
        Map<String, Object> userLoginMap = new HashMap<>();
        userLoginMap.put("userId", userInfo.getId());
        userLoginMap.put("username", userInfo.getUsername());
        String userToken = JwtUtils.genToken(userLoginMap);

        return new UserLoginResponse(userInfo.getId(), userToken);
    }
}
