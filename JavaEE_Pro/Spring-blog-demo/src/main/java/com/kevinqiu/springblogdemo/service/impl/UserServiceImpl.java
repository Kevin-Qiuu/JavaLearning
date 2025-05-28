package com.kevinqiu.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kevinqiu.springblogdemo.common.exception.BlogException;
import com.kevinqiu.springblogdemo.common.utils.BeansTransfer;
import com.kevinqiu.springblogdemo.common.utils.JwtUtils;
import com.kevinqiu.springblogdemo.common.utils.SecurityUtils;
import com.kevinqiu.springblogdemo.mapper.BlogInfoMapper;
import com.kevinqiu.springblogdemo.mapper.UserInfoMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.dataobject.UserInfo;
import com.kevinqiu.springblogdemo.pojo.request.UserLoginRequest;
import com.kevinqiu.springblogdemo.pojo.response.UserInfoResponse;
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

    @Resource(name = "blogInfoMapper")
    private BlogInfoMapper blogInfoMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        if (!StringUtils.hasLength(userLoginRequest.getUsername())
                || !StringUtils.hasLength(userLoginRequest.getPassword())) {
            return null;
        }

        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername, userLoginRequest.getUsername()));

        if (!SecurityUtils.vertify(userInfo.getPassword(), userLoginRequest.getPassword())){
            return null;
        }

        // 创建 Token
        Map<String, Object> userLoginMap = new HashMap<>();
        userLoginMap.put("userId", userInfo.getId());
        userLoginMap.put("username", userInfo.getUsername());
        String userToken = JwtUtils.genToken(userLoginMap);

        return new UserLoginResponse(userInfo.getId(), userToken);
    }

    @Override
    public UserInfoResponse getUserByUserId (String userId) {
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId, userId));
        if (userInfo == null){
            throw new BlogException("不存在 userId: " + userId);
        }
        return BeansTransfer.trans2UserInfoResponse(userInfo);
    }

    @Override
    public UserInfoResponse getUserByBlogId(String blogId) {
        BlogInfo blogInfo = blogInfoMapper.selectById(blogId);
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId, blogInfo.getUserId()));
        return BeansTransfer.trans2UserInfoResponse(userInfo);
    }
}
