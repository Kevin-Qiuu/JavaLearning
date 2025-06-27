package com.kevinqiu.langchain4jdemo.assitant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

// 通过显示装配的方式进行配置智能体
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "myChatMemory"
)
public interface MemoryChatAssistant {

    String chat(String userMessage);

}
