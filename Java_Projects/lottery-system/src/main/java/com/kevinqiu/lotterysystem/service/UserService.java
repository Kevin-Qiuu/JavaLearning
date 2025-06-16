package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.UserLoginParam;
import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.service.dto.UserBaseInfoDTO;
import com.kevinqiu.lotterysystem.service.dto.UserLoginDTO;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {

    UserRegisterDTO register(UserRegisterParam param);

    UserLoginDTO login(UserLoginParam param);

    List<UserBaseInfoDTO> findUserBaseInfo(String identity);
}
