package com.kevinqiu.lotterysystem.service.impl;

import cn.hutool.core.lang.Validator;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.RegexUtil;
import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.dao.dataobject.Encrypt;
import com.kevinqiu.lotterysystem.dao.dataobject.UserDO;
import com.kevinqiu.lotterysystem.dao.mapper.UserMapper;
import com.kevinqiu.lotterysystem.service.UserService;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;
import com.kevinqiu.lotterysystem.service.enums.UserIdentityEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Override
    public UserRegisterDTO register(UserRegisterParam param) {
        // 校验注册信息
        checkRegisterInfo(param);

        // 加密私密信息，构造 DAO 层请求数据
        UserDO userDO = new UserDO();
        userDO.setUserName(param.getName());
        userDO.setMail(param.getMail());
        userDO.setPhoneNumber(new Encrypt(param.getPhoneNumber()));
        userDO.setPassword(new Encrypt(param.getPassword()));
        userDO.setIdentity(param.getIdentity());

        // 保存信息
        userMapper.insertRegisterUser(userDO);

        // 构造返回
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId(userDO.getId());
        return userRegisterDTO;
    }

    private void checkRegisterInfo(UserRegisterParam param) {

        if (param == null) {
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_INTO_IS_NULL);
        }

        // 校验邮箱格式
        if (!Validator.isEmail(param.getMail())) {
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_IS_ILLEGAL);
        }

        // 校验手机号格式
        if (!RegexUtil.checkMobile(param.getPhoneNumber())) {
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_IS_ILLEGAL);
        }

         // 校验邮箱是否被使用
        if (userMapper.countByMail(param.getMail()) > 0){
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_IS_EXISTED);
        }

        // 校验手机号是否被使用（使用 TypeHandler）
        if(userMapper.countByPhone(new Encrypt((param.getPhoneNumber()))) > 0 ){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_IS_EXISTED);
        }

        // 校验身份信息
        if (null == UserIdentityEnum.forName(param.getIdentity())){
            throw new ServiceException(ServiceErrorCodeConstants.IDENTITY_IS_ILLEGAL);
        }

        // 校验管理员密码必填
        if (param.getIdentity().equals(UserIdentityEnum.ADMIN.name())
                && !StringUtils.hasLength(param.getPassword())){
            throw new ServiceException(ServiceErrorCodeConstants.ADMIN_PASSWORD_IS_NULL);
        }

        // 校验密码强调
        if (!RegexUtil.checkPassword(param.getPassword())){
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_IS_ILLEGAL);
        }

    }
}
