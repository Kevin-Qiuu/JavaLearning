package com.kevinqiu.lotterysystem.common.pojo;

import com.kevinqiu.lotterysystem.common.errorcode.ErrorCode;
import com.kevinqiu.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import lombok.Data;

@Data
public class CommonResult<T> {
    /**
     * 异常错误码
     */
    private Integer code;

    /**
     * 异常错误信息
     */
    private String message;

    /**
     * 正常返回的数据
     */
    private T data;

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(GlobalErrorCodeConstants.SUCCESS.getCode());
        commonResult.setData(data);
        return commonResult;
    }

    public static <T> CommonResult<T> error(Integer code, String message){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(code);
        commonResult.setMessage(message);
        return commonResult;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode){
        return error(errorCode.getCode(), errorCode.getMessage());
    }

}
