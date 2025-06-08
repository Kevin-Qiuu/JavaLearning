package com.kevinqiu.lotterysystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JacksonUtilTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void writeValueTest() {
        /*
        序列化操作会自动将一个对象转化成字节流，即文本格式，进而可以进行对象的传输
        如：CommonResult<String> success -> {"code":200,"message":"","data":"Yeeeep!"}
         */
        CommonResult<String> success = CommonResult.success("Yeeeep!");
        String content = "";
        try {
            content = objectMapper.writeValueAsString(success);
            System.out.println(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readValueTest() {
        /*
        反序列化操作，根据字节流信息读取并创建一个新对象
        {"code":200,"message":"","data":"Yeeeep!"} -> CommonResult<String> success
         */
        String content = "{\"code\":200,\"message\":\"\",\"data\":\"Yeeeep!\"}";
        try {
            CommonResult commonResult = objectMapper.readValue(content, CommonResult.class);
            System.out.println(commonResult.getData());
            System.out.println(commonResult.getCode());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writAndReadListTest() {
        List<CommonResult<String>> list = Arrays.asList(
                CommonResult.success("Yeeeep!"),
                CommonResult.error(500, "no Yeeeep!"),
                CommonResult.error(500, "no Yeeeep!")
        );
        String content = "";
        try {
            content = objectMapper.writeValueAsString(list);
            System.out.println(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(List.class, CommonResult.class);
        try {
            List<CommonResult> list1 = objectMapper.readValue(content, javaType);
            for (CommonResult commonResult : list1) {
                System.out.println(commonResult);
            }
            System.out.println(list1.get(1).getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void jacksonUtilTest() {
        /*
        频繁的使用 try-catch 来安全处理序列化和反序列化很不优雅
        为此需要集中处理异常，将处理异常的代码进行封装处理，模仿 Spring 的 tryParse 方法
         */
        CommonResult<String> success = CommonResult.success("Yeeeep!");
        String content = JacksonUtil.writeValueAsString(success);
        System.out.println(content);
        CommonResult commonResult = JacksonUtil.readValue(content, CommonResult.class);
        System.out.println(commonResult.getData());
        List<CommonResult<String>> list = Arrays.asList(
                CommonResult.success("Yeeeep!"),
                CommonResult.error(500, "no Yeeeep!"),
                CommonResult.error(500, "no Yeeeep!")
        );
        content = JacksonUtil.writeValueAsString(list);
        System.out.println(content);
        List<CommonResult> list1 = JacksonUtil.readListValue(content, CommonResult.class);
        for (CommonResult i : list1) {
            System.out.println(i);
        }
    }
}
