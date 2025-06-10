package com.kevinqiu.lotterysystem;

import cn.hutool.core.lang.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidationTest {
    @Test
    void validateMailTest(){
        boolean isEmail = Validator.isEmail("kevqiu@foxmail.com");
        System.out.println(isEmail);
    }
}
