package com.kevinqiu.springcaptchacodedemo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.kevinqiu.springcaptchacodedemo.configuration.CaptchaConfiguration;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    public static long CAPTCHA_OUT_TIME = 5 * 60 * 1000;

    private CaptchaConfiguration captchaConfiguration;

    // 使用构造函数来进行 bean 的注入
    @Autowired
    CaptchaController(CaptchaConfiguration captchaConfiguration) {
        Assert.notNull(captchaConfiguration, "CaptchaConfiguration must not be null!");
        this.captchaConfiguration = captchaConfiguration;
    }

    /*
    /captcha/getCaptcha
    获取验证码，并设置 session 存储校验信息
     */
    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) {
        ICaptcha captcha = CaptchaUtil.createLineCaptcha(captchaConfiguration.getWidth(),
                captchaConfiguration.getHeight(), captchaConfiguration.getCodeCount(), captchaConfiguration.getLineCount());
        response.setContentType("image/jpeg");
        try {
            captcha.write(response.getOutputStream());
            session.setAttribute(captchaConfiguration.getCaptchaSession().getKey(), captcha.getCode());
            session.setAttribute(captchaConfiguration.getCaptchaSession().getTimeStamp(), System.currentTimeMillis());
            System.out.println(captcha.getCode());
            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    /captcha/check
    校验验证码，并通过 cookie 来校验信息
     */
    @RequestMapping("/checkCaptcha")
    public Integer checkCaptcha(String inputCaptcha, HttpSession session) {
        if (!StringUtils.hasLength(inputCaptcha)) {
            return 0;
        }

        String captchaCode = (String) session.getAttribute(captchaConfiguration.getCaptchaSession().getKey());
        Long captchaSetTimeStamp = (Long) session.getAttribute(captchaConfiguration.getCaptchaSession().getTimeStamp());
        // session 超时丢失
        if (captchaCode == null || captchaSetTimeStamp == null) {
            return -1;
        }

        // 验证码超时
        if (System.currentTimeMillis() - captchaSetTimeStamp > CAPTCHA_OUT_TIME) {
            return -1;
        }

        // 验证码错误
        if (!inputCaptcha.equalsIgnoreCase(captchaCode)) {
            return 0;
        }

        // 验证码正确
        return 1;
    }

}
