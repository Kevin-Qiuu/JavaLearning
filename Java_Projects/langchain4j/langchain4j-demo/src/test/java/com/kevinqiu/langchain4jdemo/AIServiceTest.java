package com.kevinqiu.langchain4jdemo;

import com.kevinqiu.langchain4jdemo.assitant.MyAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTest {

    @Autowired
    private QwenChatModel qwenChatModel;

    @Autowired
    private MyAssistant myAssistant;

//    @Test
//    public void testAIService(){
//        MyAssistant myAssistant = AiServices.create(MyAssistant.class, qwenChatModel);// 实现原理基于代理
//        String answer = myAssistant.chat("你是谁？");
//        System.out.println(answer);
//    }
//

    @Test
    public void testAIService2(){
        String answer = myAssistant.chat("你是谁？");
        System.out.println(answer);
    }


}
