package com.kevinqiu.lotterysystem.common.errorcode;
import lombok.Data;

@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
