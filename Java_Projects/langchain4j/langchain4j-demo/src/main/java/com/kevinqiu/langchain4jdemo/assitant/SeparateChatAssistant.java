package com.kevinqiu.langchain4jdemo.assitant;

// 创建记忆隔离对话智能体

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "myChatMemoryProvider"
)
public interface SeparateChatAssistant {

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

}
