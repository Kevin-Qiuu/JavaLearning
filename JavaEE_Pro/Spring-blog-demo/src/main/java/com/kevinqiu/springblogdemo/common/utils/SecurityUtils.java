package com.kevinqiu.springblogdemo.common.utils;

import cn.hutool.crypto.SecureUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class SecurityUtils {

    private static String md5AddSaltInPasswd(@NotBlank String passwd, @NotBlank String salt) {
        return SecureUtil.md5(passwd.substring(0, passwd.length() / 2)
                + salt.substring(0, salt.length() / 2)
                + passwd.substring(passwd.length() / 2)
                + salt.substring(salt.length() / 2));
    }

    public static String encrypt(String inputPassword){
        String salt = UUID.randomUUID().toString().replace("-", "");
        String md5PasswdWithSalt = md5AddSaltInPasswd(inputPassword, salt);
       return md5PasswdWithSalt.substring(0, 16)
                + salt.substring(16) + md5PasswdWithSalt.substring(16)
                + salt.substring(0, 16);
    }

    public static Boolean vertify(String md5PasswdSalt, String inputPassword){
        String salt = md5PasswdSalt.substring(48) + md5PasswdSalt.substring(16, 32);
        String md5Passwd = md5PasswdSalt.substring(0, 16) + md5PasswdSalt.substring(32, 48);
        String md5InputPasswd = md5AddSaltInPasswd(inputPassword, salt);
        return md5InputPasswd.equals(md5Passwd);
    }

}


