package com.kevinqiu.springcaptchacodedemo.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "captcha")
public class CaptchaConfiguration {
    private Integer width = 200;
    private Integer height = 60;
    private Integer codeCount = 4;
    private Integer lineCount = 200;
    private CaptchaSession captchaSession;

    @Data
    public static class CaptchaSession {
        private String key = "CAPTCHA_CODE";
        private String timeStamp = "CAPTCHA_SET_TIME_STAMP";
    }
}
