package com.kevinqiu.langchain4jdemo.assitant;

// 创建记忆隔离对话智能体

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "xiaozhiChatMemoryProvider",
        tools = "calculatorTools"
)
public interface SeparateChatAssistant {

    // 系统提示词
    // 如果修改了 SystemMessage 以后，接着用之前的记忆，会使对话记忆失效
//    @SystemMessage("你是我的好朋友，你的名字叫三哥，请用东北话跟我进行对话。今天是{{current_date}}")
    @SystemMessage(fromResource = "my-prompt-template.txt")
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);

    @SystemMessage(fromResource = "my-prompt-template2.txt")
    String chat(@MemoryId int memoryId, @UserMessage String userMessage
            , @V("username") String username, @V("age") Integer age);
}
