package com.kevinqiu.lotterysystem.common.configuration;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Component
public class AppConfig implements WebMvcConfigurer {

    @Resource(name = "loginInterceptor")
    private HandlerInterceptor loginInterceptor;

    private final List<String> excludes = Arrays.asList(
            "/**/*.html",
            "/css/**",
            "/js/**",
            "/pic/**",
            "/*.jpg",
            "/*.png",
            "/favicon.ico",
            "/**/login",
            "/register",
            "/verification-code/send",
            "/winning-records/show"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludes);
    }
}
