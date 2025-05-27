package com.kevinqiu.springblogdemo.pojo.response;

import com.kevinqiu.springblogdemo.common.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{
    private Integer code;
    private String errMsg;
    private T data;

    private Result(T result){
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.errMsg = null;
        this.data = result;
    }

    public static Result<?> success(Object resultObject){
        return new Result<>(resultObject);
    }

    public static Result<?> fail(String errMsg){
        Result<?> result = new Result<>();
//        Result<Object> result = new Result<>(new Object());
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setErrMsg(errMsg);
        return result;
    }
}
