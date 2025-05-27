package com.kevinqiu.springblogdemo.service;

import com.kevinqiu.springblogdemo.pojo.request.UserLoginRequest;
import com.kevinqiu.springblogdemo.pojo.response.UserLoginResponse;

public interface UserService {
    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
