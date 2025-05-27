package com.kevinqiu.springblogdemo.common.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200),
    FAIL(-1);

    private final Integer code;

    ResultCodeEnum(Integer code){this.code = code;}

}