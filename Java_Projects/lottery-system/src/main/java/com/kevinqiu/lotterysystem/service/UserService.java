package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.UserLoginParam;
import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.service.dto.UserBaseInfoDTO;
import com.kevinqiu.lotterysystem.service.dto.UserLoginDTO;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    /**
     *  用户注册
     * @param param UserRegisterParam
     *              @see UserRegisterParam
     * @return UserRegisterDTO
     */
    UserRegisterDTO register(UserRegisterParam param);

    /**
     * 用户登录
     * @param param UserLoginParam
     *              @see UserLoginParam
     * @return UserLoginDTO
     */
    UserLoginDTO login(UserLoginParam param);


    /**
     * 根据身份查看用户基础信息
     * @param identity 用户身份
     * @return List<UserBaseInfoDTO>
     *         @see UserBaseInfoDTO
     */
    List<UserBaseInfoDTO> findUserBaseInfo(String identity);
}
