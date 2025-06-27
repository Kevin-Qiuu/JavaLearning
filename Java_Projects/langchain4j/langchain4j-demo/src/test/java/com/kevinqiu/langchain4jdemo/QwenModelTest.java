package com.kevinqiu.langchain4jdemo;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QwenModelTest {

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    void testModel(){
        // 发送询问
        String chat = qwenChatModel.chat("你是谁？");
        // 打印结果
        System.out.println(chat);
    }

}
