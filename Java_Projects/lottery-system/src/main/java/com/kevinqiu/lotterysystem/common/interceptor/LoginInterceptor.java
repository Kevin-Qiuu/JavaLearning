package com.kevinqiu.lotterysystem.common.interceptor;

import com.kevinqiu.lotterysystem.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从 Header 中获取 user_token
        String userToken = request.getHeader("user_token");
        log.info("从请求中获取 Token: {}", userToken);
        log.info("请求路径: {}", request.getRequestURI());

        // 解析 JWT
        Claims claims = JWTUtil.parseJWT(userToken);
        if (null == claims) {
            log.info("JWT 解析失败，不予授权访问！");
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
