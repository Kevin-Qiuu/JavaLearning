package com.kevinqiu.lotterysystem.common.errorcode;

public interface ControllerErrorCodeConstants {
    // ------ 人员模块错误码 --------
    ErrorCode USER_REGISTER_FAILED = new ErrorCode(101, "用户注册失败！");
    ErrorCode USER_LOGIN_FAILED = new ErrorCode(102, "用户登录失败！");


    // ------ 奖品模块错误码 --------
    ErrorCode PAGE_LIST_IS_NULL = new ErrorCode(302,"奖品页面获取失败！");


    // ------ 活动模块错误码 --------
    ErrorCode ACTIVITY_CREATE_FAILED = new ErrorCode(301, "活动创建失败！");


    // ------ 抽奖错误码 --------

}
