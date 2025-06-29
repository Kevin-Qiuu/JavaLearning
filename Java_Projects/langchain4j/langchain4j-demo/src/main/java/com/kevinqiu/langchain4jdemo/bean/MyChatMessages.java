package com.kevinqiu.langchain4jdemo.bean;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class MyChatMessages {

    // MongoDB 规定的唯一标识，映射到 MongoDB 文档的 _id 字段
    @Id
    private ObjectId messageId;

    // 自定义的记忆隔离主键
    private Integer memoryId;

    // 存储当前聊天记录列表的 json 字符串（序列化字符）
    private String content;
}
