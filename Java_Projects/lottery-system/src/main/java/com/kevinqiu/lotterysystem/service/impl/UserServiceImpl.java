package com.kevinqiu.lotterysystem.service.impl;

import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.service.UserService;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserRegisterDTO register(UserRegisterParam param) {
        // 校验注册信息

        // 加密私密信息，构造 DAO 层请求数据

        // 保存信息

        // 构造返回
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId(12L);
        return userRegisterDTO;
    }

    private void checkRegisterInfo(UserRegisterParam param){
        // 校验邮箱格式

        // 校验手机号格式

        // 校验身份信息

        // 校验管理员密码必填

        // 密码校验，至少 6 位

        // 校验邮箱是否被使用

        // 校验手机号是否被使用
        int a = 10;
        return;
    }
}
