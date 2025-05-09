package com.kevinqiu.springmvc_demos.mapper;

import com.kevinqiu.springmvc_demos.model.MessageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageInfoMapperTest {
    @Autowired
    MessageInfoMapper messageInfoMapper;
    @Test
    void selectAllMessage() {
        messageInfoMapper.selectAllMessage().stream().forEach(System.out::println);
    }

    @Test
    void insertMessage() {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setFrom("qiu");
        messageInfo.setTo("chen");
        messageInfo.setMessage("hello, honey~");
        Integer i = messageInfoMapper.insertMessage(messageInfo);
    }
}