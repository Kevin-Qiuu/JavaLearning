package com.kevinqiu.springblogdemo;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kevinqiu.springblogdemo.common.utils.SecurityUtils;
import com.kevinqiu.springblogdemo.mapper.UserInfoMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.UserInfo;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class SecurityUtilTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void updateUserPasswd() {
        List<UserInfo> userInfos = userInfoMapper.selectList(new LambdaQueryWrapper<UserInfo>()
                .gt(UserInfo::getId, 0));
        userInfos.stream().forEach((UserInfo userInfo) -> {
            userInfo.setPassword(SecurityUtils.encrypt(userInfo.getPassword()));
            userInfoMapper.updateById(userInfo);
        });
    }


    public String md5AddSaltInPasswd(@NotBlank String passwd, @NotBlank String salt) {
        return SecureUtil.md5(passwd.substring(0, passwd.length() / 2)
                + salt.substring(0, salt.length() / 2)
                + passwd.substring(passwd.length() / 2)
                + salt.substring(salt.length() / 2));
    }

    @Test
    public void encryptTest() {
        String passwd = "1234567";
        String salt = UUID.randomUUID().toString().replace("-", "");
        System.out.println(salt.length());
        String passwdWithSalt = md5AddSaltInPasswd(passwd, salt);
        String finalMd5Passwd = passwdWithSalt.substring(0, 16)
                + salt.substring(16) + passwdWithSalt.substring(16)
                + salt.substring(0, 16);
        System.out.println(finalMd5Passwd);
    }

    @Test
    public void verifyTest() {
        /*
        d84c4a3b14536c8b32cce71bdc184ad2fe1febefe01e7b2a8a67e46f9d151ef932cce71bdc184ad28a67e46f9d151ef9
         */

        /*
        md5 只能生成摘要，无法从摘要重新生成回原文
        但是我们认为只要摘要一样，则原文就一样
        例：md5(x1) = md5(x2) -> x1 = x2
        但是不保真，只能说 99.9999% 是这种情况
         */

        String md5PasswdSalt = "1aa16353e2176d678d50737dd8737c8b1e4a6bdbc9d94eed9f3925f0679d4ca7";
        String salt = md5PasswdSalt.substring(48) + md5PasswdSalt.substring(16, 32);
        String md5Passwd = md5PasswdSalt.substring(0, 16) + md5PasswdSalt.substring(32, 48);
        String inputPasswd = "1234567";
        String md5InputPasswd = md5AddSaltInPasswd(inputPasswd, salt);
        System.out.println(md5InputPasswd.equals(md5Passwd));

    }

}
