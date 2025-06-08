package com.kevinqiu.lotterysystem.common.errorcode;

/**
 * 存放全局错误信息码的实例
 * 接口中的变量默认是 public static final（不可修改、全局访问）。
 */
public interface GlobalErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(200, "成功！");
    ErrorCode ERROR = new ErrorCode(500, "错误！");
    ErrorCode UNKNOWN = new ErrorCode(999, "未知错误！");

}
