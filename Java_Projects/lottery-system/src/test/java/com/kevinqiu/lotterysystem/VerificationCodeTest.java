package com.kevinqiu.lotterysystem;

import com.kevinqiu.lotterysystem.service.VerificationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerificationCodeTest {
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Test
    void sendCodeTest(){
        verificationCodeService.sendVerificationCode("13354427769");
    }

    @Test
    void getCodeTest(){
        String verificationCode = verificationCodeService.getVerificationCode("13354427769");
        System.out.println(verificationCode.equals("0139"));
    }
}
