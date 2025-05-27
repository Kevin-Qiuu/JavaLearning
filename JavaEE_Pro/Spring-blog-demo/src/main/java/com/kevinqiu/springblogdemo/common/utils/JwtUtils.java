package com.kevinqiu.springblogdemo.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class JwtUtils {

    private final static String secretString = "KxZ0RthKFPEnIY/jaEADCFDmVz/DjWQpkV9eIovXV+M=";
    private final static SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    private final static long expiration = 2 * 60 * 60 * 1000; // token 存在两小时

    // 创建生成 JWT （Json web token）
    public static String genToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    // 解析 Jwt
    public static Claims parseToken(String token) {
        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
        Claims body = null;
        try {
            body = jwtParserBuilder.build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        return body;
    }

}
