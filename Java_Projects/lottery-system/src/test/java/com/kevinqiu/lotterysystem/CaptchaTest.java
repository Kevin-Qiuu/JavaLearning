package com.kevinqiu.lotterysystem;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.common.utils.SMSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class CaptchaTest {

    @Test
    void spugCaptchaTest() {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://push.spug.cc/send/V4v6k855D38GY2gD?name=抽奖系统&code=7788&target=13354427769&timeout=2";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);
        String response = restTemplate.getForObject(baseUrl, String.class);
        System.out.println("Response: " + response);
    }

    @Test
    void smsUtilSpugTest(){
        String templateId = "V4v6k855D38GY2gD";
        Map<String, String> map = new HashMap<>();
        map.put("name", "抽奖系统");
        map.put("code", "1111");
        map.put("targets", "19113111935");
        map.put("timeout", "3");
        String s = SMSUtil.sendVerificationCodeBySpug(templateId, JacksonUtil.writeValueAsString(map));
        System.out.println(s);
    }

}
