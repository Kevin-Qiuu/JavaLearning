package com.kevinqiu.springBookDemo.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class ErrorAdvice {

    @ExceptionHandler
    public Object handler(Exception e){
        return Result.fail("内部错误" + e.getMessage());
    }
}
