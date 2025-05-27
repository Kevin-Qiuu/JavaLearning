package com.kevinqiu.springblogdemo.common.interceptor;

import com.kevinqiu.springblogdemo.common.exception.BlogException;
import com.kevinqiu.springblogdemo.common.exception.NotLoginException;
import com.kevinqiu.springblogdemo.common.utils.JwtUtils;
import com.kevinqiu.springblogdemo.contant.LoginCheck;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 校验是否有 Token
        String userToken = request.getHeader(LoginCheck.USER_TOKEN);
        log.info("从 Http 中的 header 中获取 userToken: {}", userToken);
        if (!StringUtils.hasLength(userToken)){
            throw new NotLoginException("用户令牌获取失败！");
        }
        Claims claims = JwtUtils.parseToken(userToken);
        if (claims == null){
            log.info("用户令牌失效！");
            throw new NotLoginException("用户令牌失效");
        }
        log.info("令牌校验成功，放行！");
        return true;
    }
}
