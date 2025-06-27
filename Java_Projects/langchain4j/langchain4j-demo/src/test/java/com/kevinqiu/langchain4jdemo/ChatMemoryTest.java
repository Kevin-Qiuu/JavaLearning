package com.kevinqiu.langchain4jdemo;

import com.jayway.jsonpath.internal.path.ArraySliceToken;
import com.kevinqiu.langchain4jdemo.assitant.MemoryChatAssistant;
import com.kevinqiu.langchain4jdemo.assitant.MyAssistant;
import com.kevinqiu.langchain4jdemo.assitant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private QwenChatModel qwenChatModel;

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testChatMemory() {

        List<ChatMessage> chatMessagesList = new ArrayList<>();

        // 第一轮对话
        UserMessage userMessage1 = UserMessage.from("我是 kevin");
        // 将用户的输入存放入 list
        chatMessagesList.add(userMessage1);
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        // 将大模型的输出存放入 list
        chatMessagesList.add(aiMessage1);
        System.out.println(aiMessage1.text()); // 打印模型输出

        // 第二轮对话
        UserMessage userMessage2 = UserMessage.from("我是谁");
        // 将用户的输入存放入 list
        chatMessagesList.add(userMessage2);
        ChatResponse chatResponse2 = qwenChatModel.chat(chatMessagesList);
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        System.out.println(aiMessage2.text());
    }

    @Test
    public void testChatMemory2(){
        // 创建一个 chatMemory 组件，为 AiService 添加组件，创建一个 AIService
        // 在每次调用 AIService 的时候，会自动向 chatMemory 组件中存储当前对话
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // 创建 AIService
        MyAssistant aiService = AiServices
                .builder(MyAssistant.class)
                .chatLanguageModel(qwenChatModel)
                .chatMemory(messageWindowChatMemory)
                .build();
        String answer = aiService.chat("我是 Kevin!");
        System.out.println(answer);
        String answer2 = aiService.chat("我是谁？");
        System.out.println(answer2);
    }

    @Test
    public void testChatMemory3(){
        // 使用自定义的 Agent 调用大模型
        String answer = memoryChatAssistant.chat("我是 Kevin!");
        System.out.println(answer);
        String answer2 = memoryChatAssistant.chat("我是谁？");
        System.out.println(answer2);
    }

    @Test
    public void testChatMemory4(){
        // 使用自定义的对话隔离的 Agent
        String answer1 = separateChatAssistant.chat(1, "我是 kevin");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁？");
        System.out.println(answer2);

        // 更换对话记忆 Id，隔离
        String answer3 = separateChatAssistant.chat(2, "我是谁？");
        System.out.println(answer3);
    }

}
