package com.kevinqiu.langchain4jdemo.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public MessageWindowChatMemory myChatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public ChatMemoryProvider myChatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId).maxMessages(10).build();
    }

}
