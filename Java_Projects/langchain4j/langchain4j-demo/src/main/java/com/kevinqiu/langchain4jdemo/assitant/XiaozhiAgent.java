package com.kevinqiu.langchain4jdemo.assitant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService (
        wiringMode = AiServiceWiringMode.EXPLICIT,
//        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "xiaozhiChatMemoryProvider",
        tools = "appointmentTools", // 配置自定义的 tool
        contentRetriever = "xiaozhiPinconeContentRetriever" // 配置向量存储
)
public interface XiaozhiAgent {

    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    Flux<String> chat(@MemoryId Integer memoryId, @UserMessage String userMessage);

}
