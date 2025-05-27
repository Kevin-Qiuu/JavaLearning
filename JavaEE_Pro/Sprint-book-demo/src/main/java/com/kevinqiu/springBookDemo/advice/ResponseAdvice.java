package com.kevinqiu.springBookDemo.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice {

    @Autowired
    private ObjectMapper objectMapper;

    // 用于判断当前 Controller 的类或者对应的方法是否需要进行统一格式处理
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;  // 一般默认所有的方法都会支持 advice
    }

    // 在返回 Response 前执行，用于对返回的格式进行统一的封装
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("beforeBodyWrite..........");
        if(body instanceof String){
            try {
                // Debug 源码可以看出，beforeBodyWrite 可以正常运行，Result 对象也可以正常建立
                // 只是错误选择了 convertor，导致函数参数不匹配
                // 所以将结果转为 Json，Spring 会选择 Json 的 convertor 进行后续封装包
                // 当返回的数据是⾮字符串时,使⽤的 MappingJackson2HttpMessageConverter 写⼊返回对象.
                // 当返回的数据是字符串时， StringHttpMessageConvertor 会先被遍历到，进而认为这个 Convertor 会被使用
                return objectMapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if (body instanceof Result){
            return body;
        }
        return Result.success(body);
    }
}
