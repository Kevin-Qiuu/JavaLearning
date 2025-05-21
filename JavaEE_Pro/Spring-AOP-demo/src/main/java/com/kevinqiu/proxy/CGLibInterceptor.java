package com.kevinqiu.proxy;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class CGLibInterceptor implements MethodInterceptor {

    private Object target;

    public CGLibInterceptor(Object target){this.target = target;}

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("intercept: 开始执行");
        method.setAccessible(true);
        Object o = method.invoke(target, args);
        log.info("intercept: 执行完毕");
        return o;
    }
}
