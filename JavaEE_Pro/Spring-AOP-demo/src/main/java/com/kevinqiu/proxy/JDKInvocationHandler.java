package com.kevinqiu.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class JDKInvocationHandler implements InvocationHandler {

    private final Object target;

    JDKInvocationHandler(Object target){this.target = target;}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invoke: 开始执行");
        method.setAccessible(true);
        Object o = method.invoke(target, args);
        log.info("invoke: 执行完毕");
        return o;
    }
}
