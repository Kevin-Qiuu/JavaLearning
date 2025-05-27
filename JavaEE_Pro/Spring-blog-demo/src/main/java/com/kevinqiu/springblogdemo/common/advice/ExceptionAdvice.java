package com.kevinqiu.springblogdemo.common.advice;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.kevinqiu.springblogdemo.common.exception.BlogException;
import com.kevinqiu.springblogdemo.common.exception.NotLoginException;
import com.kevinqiu.springblogdemo.pojo.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public Result<?> notLoginExceptionHandler(NotLoginException e){
        log.error("发生 NotLoginException, e:{}", e.getErrMsg());
        return Result.fail(e.getErrMsg());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Result<?> blogExceptionHandler(BlogException e){
        log.error("发生 BlogException, e：{}", e.getResult().getErrMsg());
        return Result.fail(e.getResult().getErrMsg());
//        return "发生 BlogException";
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public Result<?> exceptionHandler(Exception e){
        log.error("发生 Exception, e", e);
        BlogException blogException = new BlogException(e.getMessage());
        return blogException.getResult();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Result<?> handlerMethodValidationExceptionHandler(HandlerMethodValidationException e){
        log.error("传递参数为空: ", e);
        return Result.fail("参数校验失败！");
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        log.error("传递参数为空: ", e);
        return Result.fail("参数校验失败！");
    }

}
