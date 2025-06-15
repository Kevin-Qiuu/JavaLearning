package com.kevinqiu.lotterysystem.service.impl;

import cn.hutool.captcha.generator.RandomGenerator;
import com.kevinqiu.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.RedisUtil;
import com.kevinqiu.lotterysystem.common.utils.RegexUtil;
import com.kevinqiu.lotterysystem.common.utils.SMSUtil;
import com.kevinqiu.lotterysystem.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    // redis 里面的 key 需要标准化，为了区分不同业务，需要给 Key 定义前缀

    private static final String VERIFICATION_CODE_PREFIX = "VERIFICATION_CODE_"; // 验证码 Key 前缀
    private static final Long TIME_OUT = 3L; // 单位：分钟
    private static final String TEMPLATE_ID = "V4v6k855D38GY2gD"; //模板 ID
    private static final RandomGenerator CODE_GENERATOR = new RandomGenerator("0123456789", 4); // 随机生成四位数字

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Boolean sendVerificationCode(String phoneNumber) {

        // 校验手机号
        if (!RegexUtil.checkMobile(phoneNumber)) {
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_IS_ILLEGAL);
        }

        // 生成验证码请求信息
        String verificationKey = VERIFICATION_CODE_PREFIX + phoneNumber;
        String verificationCode = CODE_GENERATOR.generate();

        // 发送验证码
        Map<String, String> templateParamMap = new HashMap<>();
        templateParamMap.put("name", "抽奖系统");
        templateParamMap.put("code", verificationCode);
        templateParamMap.put("targets", phoneNumber);
        templateParamMap.put("timeout", TIME_OUT.toString());
        String response = SMSUtil.sendVerificationCodeBySpug(TEMPLATE_ID,
                JacksonUtil.writeValueAsString(templateParamMap));
        Map<String, String> responseMap = JacksonUtil.readMapValue(response, String.class, String.class);
        if (!responseMap.get("code").equals(GlobalErrorCodeConstants.SUCCESS.getCode().toString())) {
            throw new ServiceException(ServiceErrorCodeConstants.VERIFICATION_CODE_CREATE_ERROR);
        }

        // 存放入 redis 中
        // VERIFICATION_CODE_133*******: code
        if (!redisUtil.set(verificationKey, verificationCode, TIME_OUT)) {
            throw new ServiceException(ServiceErrorCodeConstants.VERIFICATION_CODE_CREATE_ERROR);
        }

        return true;

    }

    @Override
    public String getVerificationCode(String phoneNumber) {
        // 校验手机号
        if (!RegexUtil.checkMobile(phoneNumber)) {
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_IS_ILLEGAL);
        }

        return redisUtil.get(VERIFICATION_CODE_PREFIX + phoneNumber);
    }

}
