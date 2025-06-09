package com.kevinqiu.lotterysystem;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecureUtilTest {

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




}
