package com.kevinqiu.lotterysystem;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

@SpringBootTest
public class JWTUtilTest {
    @Test
    public void genSecretKey() {
        // 创建密钥
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretStr = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretStr);
    }
}
