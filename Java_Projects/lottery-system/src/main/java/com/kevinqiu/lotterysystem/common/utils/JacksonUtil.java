package com.kevinqiu.lotterysystem.common.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.concurrent.Callable;

public class JacksonUtil {
    /*
    单例模式构造一个 objectMapper
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JacksonUtil(){}

    /*
    tryParse 集中处理异常捕获与处理操作
     */
    private static <T> T tryParse(Callable<T> parser) {
        return tryParse(parser, JacksonException.class);
    }

    private static <T> T tryParse(Callable<T> parser, Class<? extends Exception> check) {
        try {
            return parser.call();
        } catch (Exception ex) {
            if (check.isAssignableFrom(ex.getClass())) {
                throw new JsonParseException(ex);
            }
            throw new IllegalArgumentException();
        }
    }

    /**
     * 序列化操作
     */
    public static String writeValueAsString(Object o){
        return tryParse(() -> objectMapper.writeValueAsString(o));
    }

    /**
     * 反序列化操作
     */
    public static <T> T readValue(String content, Class<T> valueType){
        return tryParse(() -> objectMapper.readValue(content, valueType));
    }

    /**
     * 反序列化列表操作
     */
    public static <T> List<T> readListValue(String content, Class<T> elementType){
        return tryParse(() -> {
            JavaType javaType = objectMapper.getTypeFactory()
                    .constructParametricType(List.class, elementType);
            return objectMapper.readValue(content, javaType);
        });
    }

}
