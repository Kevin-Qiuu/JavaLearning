package com.kevinqiu.springBookDemo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 定义一个登录拦截器，需要实现 HandlerInterceptor
@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 调用 Controller 的方法之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);  // 设置为 False 表示如果没有 session 则不会自动创建
        if (session != null && session.getAttribute("userId") != null){
            return true;
        }
        System.out.println(request.getRequestURI());
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        String json = "{\"code\":401,\"message\":\"用户未登录，鉴权失败！\"}";
        response.getWriter().write(json);

        return false;
    }


    // 调用 Controller 的方法之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
