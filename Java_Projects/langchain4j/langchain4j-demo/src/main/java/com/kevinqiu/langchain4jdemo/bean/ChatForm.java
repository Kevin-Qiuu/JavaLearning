package com.kevinqiu.langchain4jdemo.bean;

import lombok.Data;

@Data
public class ChatForm {

    private Integer memoryId; // 记忆 Id
    private String  userMessage; // 用户问题

}
