package com.kevinqiu.lotterysystem.common.utils;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * 通过平台发送验证码
 */
public class SMSUtil {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String SPUG_URL = "https://push.spug.cc/send/";
    public static String sendVerificationCodeBySpug(String templateId, String templateParam){
        // 构造 Spug 的请求 URL
        String baseUrl = SPUG_URL + templateId + "?";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);

        // 根据序列化参数 templateParam 解析 map
        Map<String, String> map = JacksonUtil.readMapValue(templateParam, String.class, String.class);
        builder.queryParams(MultiValueMap.fromSingleValue(map));
        String finalUrl = builder.build().toUri().toString();

        // 发送 URL 请求
        return REST_TEMPLATE.getForObject(finalUrl, String.class);
    }
}
