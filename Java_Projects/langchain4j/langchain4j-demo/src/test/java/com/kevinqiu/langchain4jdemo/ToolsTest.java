package com.kevinqiu.langchain4jdemo;

import com.kevinqiu.langchain4jdemo.assitant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void toolsTest(){
        String answer = separateChatAssistant.chat(5, "6556.786514 乘 1231.6161 是多少？" +
                "3329132139898的算术平方根是多少?");
        System.out.println(answer);
    }

}
