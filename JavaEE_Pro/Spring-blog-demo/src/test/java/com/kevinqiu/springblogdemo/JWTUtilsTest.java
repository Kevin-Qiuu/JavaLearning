package com.kevinqiu.springblogdemo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JWTUtilsTest {

    /*
    JWT（Json web token）是一种开放标准（RFC 7519），用于在各方之间以安全的方式传递信息。
    JWT 的信息可以被验证和信任，因为它是数字签名的。
     */

    private final String secretString = "KxZ0RthKFPEnIY/jaEADCFDmVz/DjWQpkV9eIovXV+M=";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    private final long expiration = 10* 1000;


    @Test
    public void genSecretKey() {
        // 创建密钥
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretStr = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretStr);
    }

    @Test
    public void genJwt() {
        Map<String, Object> claim = new HashMap<>();
        claim.put("name", "KevinQiu");
        claim.put("age", 18);
        String jwt = Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
        System.out.println(jwt);

    }

    @Test
    public void parseJwt() {
        /*
        io.jsonwebtoken.ExpiredJwtException
        io.jsonwebtoken.security.SignatureException
         */
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiS2V2aW5RaXUiLCJhZ2UiOjE4LCJpYXQiOjE3NDgzMzk0MjEsImV4cCI6MTc0ODMzOTQzMX0.dbZyYnWkepijSdCcWDX3E9aX23UBWpBC4Sr8T6FeSlg";
        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
        Claims body = null;
        try {
            body = jwtParserBuilder.build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            System.out.println("token 已过期！");
        } catch (SignatureException e){
            System.out.println("token 签名被更改！");
        } catch (Exception e){
            System.out.println("Token 已失效！");
        }
        System.out.println(body);
    }

}
