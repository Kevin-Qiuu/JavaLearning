package com.kevinqiu.springmvc_demos.service;

import com.kevinqiu.springmvc_demos.mapper.MessageInfoMapper;
import com.kevinqiu.springmvc_demos.model.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageInfoService {
    @Autowired
    private MessageInfoMapper messageMapper;
    public List<MessageInfo> selectAllMessage(){
        return messageMapper.selectAllMessage();
    }

    public Integer insertMessage(MessageInfo messageInfo){
        return messageMapper.insertMessage(messageInfo);
    }
}
