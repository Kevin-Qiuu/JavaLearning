package com.kevinqiu.springblogdemo.common.utils;

import com.kevinqiu.springblogdemo.pojo.dataobject.UserInfo;
import com.kevinqiu.springblogdemo.pojo.response.UserInfoResponse;
import com.kevinqiu.springblogdemo.pojo.response.UserLoginResponse;
import org.springframework.beans.BeanUtils;

public class BeansTransfer {
    public static UserLoginResponse trans2UserLogin(UserInfo userInfo) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        BeanUtils.copyProperties(userInfo, userLoginResponse);
        return userLoginResponse;
    }

    public static UserInfoResponse trans2UserInfo(UserInfo userInfo) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(userInfo, userInfoResponse);
        return userInfoResponse;
    }
}
