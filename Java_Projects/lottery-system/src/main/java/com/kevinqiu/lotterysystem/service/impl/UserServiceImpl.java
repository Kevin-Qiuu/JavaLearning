package com.kevinqiu.lotterysystem.service.impl;

import cn.hutool.core.lang.Validator;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JWTUtil;
import com.kevinqiu.lotterysystem.common.utils.RegexUtil;
import com.kevinqiu.lotterysystem.common.utils.SecureUtil;
import com.kevinqiu.lotterysystem.controller.param.UserLoginParam;
import com.kevinqiu.lotterysystem.controller.param.UserMessageLoginParam;
import com.kevinqiu.lotterysystem.controller.param.UserPasswordLoginParam;
import com.kevinqiu.lotterysystem.controller.param.UserRegisterParam;
import com.kevinqiu.lotterysystem.dao.dataobject.Encrypt;
import com.kevinqiu.lotterysystem.dao.dataobject.UserBaseInfoDO;
import com.kevinqiu.lotterysystem.dao.dataobject.UserDO;
import com.kevinqiu.lotterysystem.dao.mapper.UserMapper;
import com.kevinqiu.lotterysystem.service.UserService;
import com.kevinqiu.lotterysystem.service.VerificationCodeService;
import com.kevinqiu.lotterysystem.service.dto.UserBaseInfoDTO;
import com.kevinqiu.lotterysystem.service.dto.UserLoginDTO;
import com.kevinqiu.lotterysystem.service.dto.UserRegisterDTO;
import com.kevinqiu.lotterysystem.service.enums.UserIdentityEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Resource(name = "secureUtil")
    private SecureUtil secureUtil;

    @Resource(name = "verificationCodeServiceImpl")
    private VerificationCodeService verificationCodeService;

    /**
     * 用户注册
     * @param param
     * @return
     */
    @Override
    public UserRegisterDTO register(UserRegisterParam param) {
        // 校验注册信息
        checkRegisterInfo(param);

        // 加密私密信息，构造 DAO 层请求数据
        UserDO userDO = new UserDO();
        userDO.setUserName(param.getName());
        userDO.setEmail(param.getMail());
        userDO.setPhoneNumber(new Encrypt(param.getPhoneNumber()));
        if (StringUtils.hasText(param.getPassword())) {
            userDO.setPassword(secureUtil.encryptPassword(param.getPassword()));
        }
        userDO.setIdentity(param.getIdentity());

        // 保存信息
        userMapper.insertRegisterUser(userDO);

        // 构造返回
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId(userDO.getId());
        return userRegisterDTO;
    }

    /**
     * 用户登录接口
     * @param loginParam
     * @return
     */
    @Override
    public UserLoginDTO login(UserLoginParam loginParam) {
        // todo
        // 判断用户的登录方式（密码还是验证码）
        // 使用 instance 来判断当前用户的登录方式
        if (loginParam instanceof UserPasswordLoginParam passwordLoginParam) {
            return loginByPassword(passwordLoginParam);
        } else if (loginParam instanceof UserMessageLoginParam messageLoginParam) {
            return loginByMessage(messageLoginParam);
        } else {
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_USER_INFO_IS_NULL);
        }
    }

    /**
     * 查找用户基础信息
     * @param identity
     * @return
     */
    @Override
    public List<UserBaseInfoDTO> findUserBaseInfo(String identity) {
        // 如果 identity 为 null 则查询全部用户
        List<UserBaseInfoDO> userBaseInfoDOList = userMapper.selectByIdentity(identity);
        // 使用 List 的 stream 进行快速操作
        return userBaseInfoDOList.stream().map(userBaseInfoDO -> {
            UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
            userBaseInfoDTO.setUserName(userBaseInfoDO.getUserName());
            userBaseInfoDTO.setUserId(userBaseInfoDO.getId().toString());
            userBaseInfoDTO.setIdentity(UserIdentityEnum.forIdentityMessage(userBaseInfoDO.getIdentity()));
            return userBaseInfoDTO;
        }).toList();
    }

    /**
     * 通过密码进行登录
     * @param loginParam 密码登录参数
     * @return UserLoginDTO
     */
    private UserLoginDTO loginByPassword(UserPasswordLoginParam loginParam) {
        // todo
        UserDO userDO = null;
        // 判断当前用户的登录方式（邮箱还是手机）
        if (RegexUtil.checkMail(loginParam.getLoginName())) {
            // 邮箱登录
            // 校验邮箱是否存在
            userDO = userMapper.selectByMail(loginParam.getLoginName());
        } else if (RegexUtil.checkMobile(loginParam.getLoginName())) {
            // 手机登录
            // 校验手机是否存在
            userDO = userMapper.selectByMobile(new Encrypt(loginParam.getLoginName()));
        } else {
            // 用户名不存在
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_NAME_IS_ILLEGAL);
        }

        // 强制校验用户身份，管理员必须输入密码
        if (null == userDO) {
            throw new ServiceException(ServiceErrorCodeConstants.USER_IS_NOT_EXISTED);
        }

        // 校验用户身份信息
        checkUserIdentity(loginParam.getMandatoryIdentity(), userDO.getIdentity());

        // 校验密码
        if (loginParam.getMandatoryIdentity()
                .equalsIgnoreCase(UserIdentityEnum.ADMIN.name())) {
            if (!secureUtil.validatePassword(loginParam.getPassword(), userDO.getPassword())) {
                throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_IS_WRONG);
            }
        }

        // 颁发 JWT 令牌
        return promUserJWT(userDO);
    }

    /**
     * 通过短信验证码就行登录
     * @param loginParam 短信验证码登录参数
     * @return UserLoginDTO
     */
    private UserLoginDTO loginByMessage(UserMessageLoginParam loginParam) {
        // 判断用户电话号码是否合法
        if (!RegexUtil.checkMobile(loginParam.getPhoneNumber())) {
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_NAME_IS_ILLEGAL);
        }

        // 判断电话号码是否存在
        UserDO userDO = userMapper.selectByMobile(new Encrypt(loginParam.getPhoneNumber()));
        if (null == userDO) {
            throw new ServiceException(ServiceErrorCodeConstants.USER_IS_NOT_EXISTED);
        }

        // 校验用户身份信息
        checkUserIdentity(loginParam.getMandatoryIdentity(), userDO.getIdentity());

        // 判断验证码是否存在和验证码是否正确
        String systemVerificationCode = verificationCodeService.getVerificationCode(loginParam.getPhoneNumber());
        if (!StringUtils.hasText(systemVerificationCode)
                || !systemVerificationCode.equals(loginParam.getVerificationCode())) {
            throw new ServiceException((ServiceErrorCodeConstants.VERIFICATION_CODE_IS_ERROR));
        }

        // 颁布 JWT
        return promUserJWT(userDO);
    }

    /**
     * 校验用户身份信息
     * @param loginIdentity 登录身份
     * @param userDOIdentity 对应数据库中的用户身份
     */
    private void checkUserIdentity(String loginIdentity, String userDOIdentity) {
        if (!StringUtils.hasText(userDOIdentity)
                || !StringUtils.hasText(loginIdentity)
                || !StringUtils.hasText(UserIdentityEnum.forName(loginIdentity))
                || !userDOIdentity.equalsIgnoreCase(loginIdentity)) {
            /*
            1. 用户登录信息中没有身份信息
            2. 用户登录信息中的身份信息与系统规定角色不匹配
            3. 用户登录信息与数据库中存储信息不匹配
             */
            throw new ServiceException(ServiceErrorCodeConstants.IDENTITY_IS_ILLEGAL);
        }
    }

    private UserLoginDTO promUserJWT(UserDO userDO) {
        // 颁发 JWT 令牌
        // JWT 中存放用户的 ID 和身份信息
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("userId", userDO.getId());
        userClaims.put("identity", userDO.getIdentity());
        String userToken = JWTUtil.genJwt(userClaims);

        // 创建 UserLoginDTO
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setToken(userToken);
        userLoginDTO.setIdentity(userDO.getIdentity());

        return userLoginDTO;
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
        if (userMapper.countByMail(param.getMail()) > 0) {
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_IS_EXISTED);
        }

        // 校验手机号是否被使用（使用 TypeHandler）
        if (userMapper.countByPhone(new Encrypt((param.getPhoneNumber()))) > 0) {
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_IS_EXISTED);
        }

        // 校验身份信息
        if (null == UserIdentityEnum.forName(param.getIdentity())) {
            throw new ServiceException(ServiceErrorCodeConstants.IDENTITY_IS_ILLEGAL);
        }

        // 校验管理员密码必填
        if (param.getIdentity().equals(UserIdentityEnum.ADMIN.name())
                && !StringUtils.hasLength(param.getPassword())) {
            throw new ServiceException(ServiceErrorCodeConstants.ADMIN_PASSWORD_IS_NULL);
        }

        // 校验密码强调
        if (!RegexUtil.checkPassword(param.getPassword())) {
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_IS_ILLEGAL);
        }

    }
}
