package com.kevinqiu.lotterysystem.common.exception;

import com.kevinqiu.lotterysystem.common.errorcode.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

// @Data  用于实现当前类的 Equal 和 hashcode 方法
// @EqualsAndHashCode(callSuper  = true) 表示在Equal和 Hashcode 方法中要考虑父类的字段

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException{

    /**
     * 异常码
     */
    public Integer code;

    /**
     * 异常信息
     */
    public String message;

    // 为了序列化
    ServiceException(){

    }

    public ServiceException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public ServiceException(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
