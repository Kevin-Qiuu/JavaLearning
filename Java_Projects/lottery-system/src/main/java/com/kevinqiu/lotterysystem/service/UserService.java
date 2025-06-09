package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;

public interface UserService {

    UserRegisterDTO register(UserRegisterParam param);


}
