package com.kevinqiu.lotterysystem.common.errorcode;

public interface ControllerErrorCodeConstants {
    // ------ 人员模块错误码 --------
    ErrorCode USER_REGISTER_FAILED = new ErrorCode(101, "用户注册失败！");
    ErrorCode USER_LOGIN_FAILED = new ErrorCode(102, "用户登录失败！");


    // ------ 奖品模块错误码 --------
    ErrorCode PRIZE_PAGE_LIST_IS_NULL = new ErrorCode(301, "奖品页面获取失败！");
    ErrorCode PRIZE_PAGE_PARAM_IS_NULL = new ErrorCode(302, "奖品翻页数据为空！");
    ErrorCode PRIZE_INFO_IS_NULL = new ErrorCode(303, "奖品信息获取失败！");


    // ------ 活动模块错误码 --------
    ErrorCode ACTIVITY_CREATE_FAILED = new ErrorCode(401, "活动创建失败！");
    ErrorCode ACTIVITY_PAGE_PARAM_IS_NULL = new ErrorCode(402, "活动翻页数据为空！");
    ErrorCode ACTIVITY_PAGE_LIST_IS_NULL = new ErrorCode(403, "活动页面获取失败！");
    ErrorCode ACTIVITY_ID_IS_NULL = new ErrorCode(404, "图像 Id 为空！");
    ErrorCode ACTIVITY_DETAIL_IS_NULL = new ErrorCode(405, "活动详细信息为空！");


    // ------ 抽奖错误码 --------

}
