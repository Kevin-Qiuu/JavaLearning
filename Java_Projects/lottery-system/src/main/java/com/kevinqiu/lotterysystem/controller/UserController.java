package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.RegexUtil;
import com.kevinqiu.lotterysystem.controller.param.UserMessageLoginParam;
import com.kevinqiu.lotterysystem.controller.param.UserPasswordLoginParam;
import com.kevinqiu.lotterysystem.controller.result.UserBaseInfoResult;
import com.kevinqiu.lotterysystem.controller.result.UserLoginResult;
import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.controller.result.UserRegisterResult;
import com.kevinqiu.lotterysystem.service.UserService;
import com.kevinqiu.lotterysystem.service.VerificationCodeService;
import com.kevinqiu.lotterysystem.service.dto.UserBaseInfoDTO;
import com.kevinqiu.lotterysystem.service.dto.UserLoginDTO;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {


    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "verificationCodeServiceImpl")
    private VerificationCodeService verificationCodeService;

    /**
     * 用户注册
     * @param param UserRegisterParam
     * @return CommonResult
     */
    @RequestMapping("/register")
    public CommonResult<UserRegisterResult> userRegister(@Validated @RequestBody UserRegisterParam param){
        log.info("userRegister -> UserRegisterParam: {}", JacksonUtil.writeValueAsString(param));
        UserRegisterDTO userRegisterDTO = userService.register(param);
        return CommonResult.success(convertUserRegisterDTO(userRegisterDTO));
    }

    /**
     * 通过密码进行登录
     * @param param UserPasswordLoginParam
     * @return CommonResult
     */
    @RequestMapping("/password/login")
    public CommonResult<UserLoginResult> userLoginByPassword(@Validated @RequestBody UserPasswordLoginParam param){
        log.info("userLoginByPassword -> UserPasswordLoginParam: {}", JacksonUtil.writeValueAsString(param));
        UserLoginDTO userLoginDTO = userService.login(param);
        return CommonResult.success(convertUserLoginDTO(userLoginDTO));
    }

    /**
     * 短信验证码登录
     * @param param UserMessageLoginParam
     * @return CommonResult
     */
    @RequestMapping("/message/login")
    public CommonResult<UserLoginResult> userLoginByMessage(@Validated @RequestBody UserMessageLoginParam param){
        log.info("userLoginByMessage -> UserMessageLoginParam: {}", JacksonUtil.writeValueAsString(param));
        UserLoginDTO userLoginDTO = userService.login(param);
        return CommonResult.success(convertUserLoginDTO(userLoginDTO));
    }

    /**
     * 发送验证码
     * @param phoneNumber 电话号码
     * @return CommonResult
     */
    @RequestMapping("/verification-code/send")
    public CommonResult<Boolean> sendVerificationCode(String phoneNumber){
        log.info("sendVerificationCode -> phoneNumber: {}", phoneNumber);
        verificationCodeService.sendVerificationCode(phoneNumber);
        return CommonResult.success(Boolean.TRUE);
    }

    /**
     * 查看用户信息
     * @param identity 用户身份
     * @return CommonResult
     */
    @RequestMapping("/base-user/find-list")
    public CommonResult<List<UserBaseInfoResult>> findUserBaseInfo(String identity){
        log.info("findUserBaseInfo");
        List<UserBaseInfoDTO> userBaseInfoDTOList = userService.findUserBaseInfo(identity);
        return CommonResult.success(userBaseInfoDTOList.stream().map(userBaseInfoDTO -> {
            UserBaseInfoResult userBaseInfoResult = new UserBaseInfoResult();
            userBaseInfoResult.setUserName(userBaseInfoDTO.getUserName());
            userBaseInfoResult.setUserId(userBaseInfoDTO.getUserId());
            userBaseInfoResult.setIdentity(userBaseInfoDTO.getIdentity());
            return userBaseInfoResult;
        }).toList());
    }

    private UserLoginResult convertUserLoginDTO(UserLoginDTO userLoginDTO) {
        if(null == userLoginDTO){
            throw new ControllerException(ControllerErrorCodeConstants.USER_LOGIN_FAILED);
        }
        UserLoginResult userLoginResult = new UserLoginResult();
        userLoginResult.setIdentity(userLoginDTO.getIdentity());
        userLoginResult.setToken(userLoginDTO.getToken());
        return userLoginResult;
    }

    private UserRegisterResult convertUserRegisterDTO(UserRegisterDTO userRegisterDTO){
        if (null == userRegisterDTO){
            throw new ControllerException(ControllerErrorCodeConstants.USER_REGISTER_FAILED);
        }
        UserRegisterResult userRegisterResult = new UserRegisterResult();
        userRegisterResult.setUserId(userRegisterDTO.getUserId());
        return userRegisterResult;
    }

}
