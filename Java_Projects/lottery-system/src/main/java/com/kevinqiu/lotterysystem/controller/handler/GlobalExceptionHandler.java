package com.kevinqiu.lotterysystem.controller.handler;

import com.kevinqiu.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 处理全局产生的异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> ServiceExceptionHandler(ServiceException serviceException){
        log.error("ServiceException -> exceptionCode: {}, exceptionMessage: {}",
                serviceException.getCode(), serviceException.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.ERROR.getCode(),
                serviceException.getMessage());
    }

    @ExceptionHandler(value = ControllerException.class)
    public CommonResult<?> ControllerExceptionHandler(ControllerException controllerException){
        log.error("ControllerException -> exceptionCode: {}, exceptionMessage: {}",
                controllerException.getCode(), controllerException.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.ERROR.getCode(),
                controllerException.getMessage());
    }

}
