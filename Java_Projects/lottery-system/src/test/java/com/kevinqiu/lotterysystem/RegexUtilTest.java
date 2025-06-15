package com.kevinqiu.lotterysystem;

import com.kevinqiu.lotterysystem.common.utils.RegexUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegexUtilTest {
    @Test
    void test(){
        boolean b = RegexUtil.checkMail("kevinqiu@foxmail.com");
        System.out.println(b);
    }

}
