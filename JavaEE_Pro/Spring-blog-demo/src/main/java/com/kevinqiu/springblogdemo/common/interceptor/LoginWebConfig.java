package com.kevinqiu.springblogdemo.common.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginWebConfig implements WebMvcConfigurer {

    @Resource(name = "loginInterceptor")
    public LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/blog/blog-editormd/**")
                .excludePathPatterns("/blog/css/**")
                .excludePathPatterns("/blog/js/**")
                .excludePathPatterns("/blog/pic/**")
                .excludePathPatterns("/blog/**.html")
                .excludePathPatterns("/favicon.ico");
    }
}
