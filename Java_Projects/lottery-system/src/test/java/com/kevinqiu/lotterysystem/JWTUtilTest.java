package com.kevinqiu.lotterysystem;

import com.kevinqiu.lotterysystem.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JWTUtilTest {
    @Test
    public void genSecretKey() {
        // 创建密钥
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretStr = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretStr);
    }

    @Test
    public void genJWTTest(){
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("userId", "123");
        userClaims.put("Identity", "admin");
        String jwt = JWTUtil.genJwt(userClaims);
        System.out.println(jwt);
        /*
        eyJhbGciOiJIUzI1NiJ9.eyJJZGVudGl0eSI6ImFkbWluIiwidXNlcklkIjoiMTIzIiwiaWF0IjoxNzQ5OTcyMTEzLCJleHAiOjE3NDk5NzU3MTN9.vajtq7yyhoFbiq_bren0-hPI_IJgQon7usXC61YdrMI
         */
    }

    @Test
    public void parseJWTTest(){
        Claims claims = JWTUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJJZGVudGl0eSI6ImFkbWluIiwidXNlcklkIjoiMTIzIiwiaWF0IjoxNzQ5OTcyMTEzLCJleHAiOjE3NDk5NzU3MTN9.vajtq7yyhoFbiq_bren0-hPI_IJgQon7usXC61YdrMI");
        Map<String, Object> userClaim = new HashMap<>(claims);
        System.out.println(userClaim.get("userId"));
        System.out.println(userClaim.get("Identity"));
    }
}
