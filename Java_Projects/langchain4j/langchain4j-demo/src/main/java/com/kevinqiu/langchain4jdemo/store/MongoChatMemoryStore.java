package com.kevinqiu.langchain4jdemo.store;

import com.kevinqiu.langchain4jdemo.bean.MyChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = Query.query(criteria);
        MyChatMessages myChatMessages = mongoTemplate.findOne(query, MyChatMessages.class);
        if (myChatMessages == null){
            // 判空处理，防止空指针异常
            return new LinkedList<>();
        }
        return ChatMessageDeserializer.messagesFromJson(myChatMessages.getContent());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> chatMessages) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = Query.query(criteria);
        Update update = new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(chatMessages));
        // 根据query条件能查询出文档，则修改文档；否则新增文档
        mongoTemplate.upsert(query, update, MyChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = Query.query(criteria);
        mongoTemplate.remove(query, MyChatMessages.class);
    }
}
