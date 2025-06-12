package com.kevinqiu.lotterysystem.service;

public interface VerificationCodeService {

    /**
     * 向目标手机号发送验证码
     * @param phoneNumber  目标手机号
     */
    void sendVerificationCode(String phoneNumber);

    /**
     * 从内存中获取目标手机的验证码
     * @param phoneNumber  目标手机号
     * @return String
     */
    String getVerificationCode(String phoneNumber);
}
