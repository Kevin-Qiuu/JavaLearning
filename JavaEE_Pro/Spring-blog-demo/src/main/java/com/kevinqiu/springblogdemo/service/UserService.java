package com.kevinqiu.springblogdemo.service;

import com.kevinqiu.springblogdemo.pojo.request.UserLoginRequest;
import com.kevinqiu.springblogdemo.pojo.response.UserInfoResponse;
import com.kevinqiu.springblogdemo.pojo.response.UserLoginResponse;

public interface UserService {
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserInfoResponse getUserByUserId(String userId);

    UserInfoResponse getUserByBlogId(String blogId);
}
