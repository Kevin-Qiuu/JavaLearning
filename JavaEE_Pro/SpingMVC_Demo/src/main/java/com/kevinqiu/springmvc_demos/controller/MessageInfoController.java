package com.kevinqiu.springmvc_demos.controller;


import com.kevinqiu.springmvc_demos.model.MessageInfo;
import com.kevinqiu.springmvc_demos.service.MessageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageInfoController {
    private static final Logger log = LoggerFactory.getLogger(MessageInfoController.class);
    @Autowired
    private MessageInfoService messageInfoService;

    private List<MessageInfo> messageList = new ArrayList<>();

    @RequestMapping("/getMsg")
    // SpringMVC 会根据返回类型自动分配响应数据报文的 Content-type
    public List<MessageInfo> getMessages(){
        messageList = messageInfoService.selectAllMessage();
        return messageList;
    }

    @RequestMapping(value = "/publish", produces = "application/json")
    public String publish(@RequestBody MessageInfo msg){
//        log.trace(msg.toString());
        System.out.println(msg);
        // 参数校验
        if (!StringUtils.hasLength(msg.getFrom()) ||
            !StringUtils.hasLength(msg.getTo()) ||
            !StringUtils.hasLength(msg.getMessage())) {
            return "{\"ok\":0}";
        }
        // 需要调用数据库插入信息
        messageInfoService.insertMessage(msg);
        messageList.add(msg);
        return "{\"ok\":1}";
    }

}
