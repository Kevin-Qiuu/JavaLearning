package com.kevinqiu.lotterysystem.common.errorcode;

public interface ServiceErrorCodeConstants {
    // ------ 人员模块错误码 --------
    ErrorCode REGISTER_INTO_IS_NULL = new ErrorCode(100, "注册信息为 null ！");
    ErrorCode MAIL_IS_ILLEGAL = new ErrorCode(101, "邮箱参数非法！");
    ErrorCode PHONE_NUMBER_IS_ILLEGAL = new ErrorCode(102, "电话号码参数非法！");
    ErrorCode IDENTITY_IS_ILLEGAL = new ErrorCode(103, "身份信息非法！");
    ErrorCode ADMIN_PASSWORD_IS_NULL = new ErrorCode(104, "管理员密码为空");
    ErrorCode PASSWORD_IS_ILLEGAL = new ErrorCode(105, "密码强度非法！");
    ErrorCode MAIL_IS_EXISTED = new ErrorCode(106, "邮箱已存在！");
    ErrorCode PHONE_NUMBER_IS_EXISTED = new ErrorCode(107, "电话号码已存在！");
    ErrorCode VERIFICATION_CODE_CREATE_ERROR = new ErrorCode(108, "验证码生成失败！");

    // ------ 奖品模块错误码 --------


    // ------ 活动模块错误码 --------


    // ------ 抽奖错误码 --------


}
