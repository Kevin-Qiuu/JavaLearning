package com.kevinqiu.langchain4jdemo.assitant;


import dev.langchain4j.service.spring.AiService;

@AiService // 添加这个注解，会自动生成代理对象，并可以直接在进行注入
public interface MyAssistant {

    String chat(String userMessage);

}
