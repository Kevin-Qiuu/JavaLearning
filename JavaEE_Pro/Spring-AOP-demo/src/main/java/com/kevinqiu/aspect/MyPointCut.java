package com.kevinqiu.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 对于只想在某些 Controller 的部分方法中添加 AOP 的场景
// 可以采用自定义注解的方式实现 AOP
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyPointCut {

}
