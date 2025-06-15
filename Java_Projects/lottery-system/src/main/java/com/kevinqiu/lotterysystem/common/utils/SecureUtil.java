package com.kevinqiu.lotterysystem.common.utils;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.UUID;

@Component
public class SecureUtil {

    // 生成指定长度的随机字符串
    public String encryptPassword(String password) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        return DigestUtil.sha256Hex(password + salt) + salt;
    }

    public Boolean validatePassword(String inputPassword, String realPassword) {
        if (!StringUtils.hasText(realPassword)) {
            return false;
        }
        String salt = realPassword.substring(realPassword.length() - 32);
        String encryptPassword = DigestUtil.sha256Hex(inputPassword + salt) + salt;
        return encryptPassword.equals(realPassword);
    }
}
