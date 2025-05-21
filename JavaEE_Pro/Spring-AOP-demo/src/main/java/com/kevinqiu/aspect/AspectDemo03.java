package com.kevinqiu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AspectDemo03 {
    @Pointcut("@annotation(com.kevinqiu.aspect.MyPointCut)")
    public void pt(){}

    @Around("pt()")
    public Object aroundMyAnnotationAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("AroundDemo03 -> aroundMyAnnotationAdvice: Start.");
        Object o = pjp.proceed();
        log.info("AroundDemo03 -> aroundMyAnnotationAdvice: End.");
        return o;
    }
}
