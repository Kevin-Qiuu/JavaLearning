package com.kevinqiu.springblogdemo.common.exception;

import com.kevinqiu.springblogdemo.pojo.response.Result;
import com.kevinqiu.springblogdemo.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.Getter;

@Data
public class BlogException extends RuntimeException{
    private Result<?> result;
    public BlogException(String errMsg){
        result = Result.fail(errMsg);
    }
}
