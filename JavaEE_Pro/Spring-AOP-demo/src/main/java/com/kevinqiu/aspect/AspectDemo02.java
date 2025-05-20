package com.kevinqiu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Aspect  // 只有被该注解修饰才认为是切面类
@Component
public class AspectDemo02 {
     // 定义切点
    @Pointcut("execution(* com.kevinqiu.controller.*.*(..))")
    private void pointcut() {
    }


    // 定义环绕通知
    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("开始执行方法，计时开始");
        long startTime = System.currentTimeMillis();
        Object o = pjp.proceed();
        long endTime = System.currentTimeMillis();
        log.info("方法执行完毕，耗时 {} ms", endTime - startTime);
        return o;
    }

    // 定义方法调用前通知
    @Before("pointcut()")
    public void beforeAdvice(){
        log.info("Before 切面触发");
    }

    // 定义方法调用后通知
    @After("pointcut()")
    public void afterAdvice(){
        log.info("After 切面触发");
    }

    // 定义方法返回值前通知
    @AfterReturning("pointcut()")
    public void afterReturningAdvice(){
        log.info("AfterReturning 切面触发");
    }
}
