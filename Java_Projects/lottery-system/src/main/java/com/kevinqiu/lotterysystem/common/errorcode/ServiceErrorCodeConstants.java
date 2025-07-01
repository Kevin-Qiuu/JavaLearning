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
    ErrorCode LOGIN_USER_INFO_IS_NULL = new ErrorCode(109, "用户登录信息为空！");
    ErrorCode USER_IS_NOT_EXISTED = new ErrorCode(110, "当前用户不存在！");
    ErrorCode PASSWORD_IS_WRONG = new ErrorCode(111, "密码错误！");
    ErrorCode VERIFICATION_CODE_IS_ERROR = new ErrorCode(112, "验证码错误！");
    ErrorCode LOGIN_NAME_IS_ILLEGAL = new ErrorCode(113, "用户名格式非法！");

    // ------ 奖品模块错误码 --------
    ErrorCode PAGE_LIST_IS_NULL = new ErrorCode(201, "奖品页面列表为空！");



    // ------ 活动模块错误码 --------
    ErrorCode CREATE_ACTIVITY_INFO_IS_EMPTY = new ErrorCode(301, "活动信息为空！");
    ErrorCode ACTIVITY_USER_INFO_ERROR = new ErrorCode(302, "活动人员信息错误！");
    ErrorCode ACTIVITY_PRIZE_INFO_ERROR = new ErrorCode(302, "活动奖品信息错误！");
    ErrorCode ACTIVITY_USER_COUNT_LESS_THAN_PRIZE_COUNT = new ErrorCode(303, "活动人员数量小于奖品数量！");
    ErrorCode ACTIVITY_PRIZE_TIERS_ERROR = new ErrorCode(304, "奖品等级参数错误！");
    ErrorCode CACHE_ACTIVITY_INFO_IS_NULL = new ErrorCode(306, "缓存奖品信息为空！");


    // ------ 抽奖错误码 --------


    // ------ 文件上传错误码 ------
    ErrorCode MAKE_DIR_ERROR = new ErrorCode(501, "创建文件夹失败！");
    ErrorCode ORIGINAL_FILE_ERROR = new ErrorCode(502, "上传文件错误！");
    ErrorCode FILE_SAVE_ERROR = new ErrorCode(503, "文件存储失败！");
    ErrorCode MAX_UPLOAD_SIZE_EXCEEDED = new ErrorCode(504, "上传的文件体积过大！");
}
