package com.kevinqiu.lotterysystem;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecureUtilTest {

    @Autowired
    com.kevinqiu.lotterysystem.common.utils.SecureUtil secureUtil;

    @Test
    void aesEncryptTest(){
        byte[] kit = "12345678abcdefgh".getBytes();
        String secretInfo = "13344447777";
        // 加密
        String aesRet = SecureUtil.aes(kit).encryptHex(secretInfo);
        System.out.println(aesRet);
        // 解密
        String desRet = SecureUtil.aes(kit).decryptStr(aesRet);
        System.out.println(desRet);
    }

    @Test
    void hashEncryptTest(){
        String secretInfo = "13344447777";
        String s = DigestUtil.sha256Hex(secretInfo);
        System.out.println(s);
    }

    @Test
    void passwdEncryptTest(){
        System.out.println(secureUtil.encryptPassword("123456"));
    }

    @Test
    void validateTest(){
        System.out.println(secureUtil.validatePassword("123456"
                , "0a46d462ea85d9e0a9a062b75e3d9c3a42a013b93338adb676e0172c3247537bccdfb0b0c83d4ef096d48e8aa4531dc3"));
    }




}
