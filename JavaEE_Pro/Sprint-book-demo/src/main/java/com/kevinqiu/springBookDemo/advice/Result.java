package com.kevinqiu.springBookDemo.advice;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String errMsg;
    private T data;

    public static Result<Object> success(Object data){
        Result<Object> result = new Result<>();
        result.setCode(200);
        result.setErrMsg("");
        result.setData(data);
        return result;
    }

    public static Result<String> fail(String msg){
        Result<String> result = new Result<>();
        result.setCode(404);
        result.setErrMsg(msg);
        result.setData("");
        return result;
    }
}
