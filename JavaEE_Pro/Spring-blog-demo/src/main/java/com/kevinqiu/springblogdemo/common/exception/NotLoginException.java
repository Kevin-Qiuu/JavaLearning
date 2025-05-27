package com.kevinqiu.springblogdemo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotLoginException extends RuntimeException{
    String errMsg;
}
