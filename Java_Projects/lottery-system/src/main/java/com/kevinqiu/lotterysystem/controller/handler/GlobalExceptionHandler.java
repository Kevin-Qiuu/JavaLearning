package com.kevinqiu.lotterysystem.controller.handler;

import com.kevinqiu.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.exception.ServiceException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        log.error("ControllerException -> otherException: ", ex);
        StringBuilder errorMsg = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorMsg.append(error.getDefaultMessage()).append("\n");
        });
        return CommonResult.error(GlobalErrorCodeConstants.ERROR.getCode(),
                errorMsg.toString());
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> AllExceptionHandler(Exception ex){
        log.error("AllExceptionHandler -> Exception: ", ex);
        return CommonResult.error(GlobalErrorCodeConstants.ERROR.getCode(),
                ex.toString());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public CommonResult<?> MaxUploadSizeExceededExceptionHandler(Exception exception){
        log.error("MaxUploadSizeExceededExceptionHandler -> exception");
        return CommonResult.error(ServiceErrorCodeConstants.MAX_UPLOAD_SIZE_EXCEEDED);
    }


}
