package com.kevinqiu.springmvc_demos.msgWall;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MsgController {
    private final List<Message> messageList = new ArrayList<>();

    @RequestMapping("/getMsg")
    // SpringMVC 会根据返回类型自动分配响应数据报文的 Content-type
    public List<Message> getMessages(){
        return messageList;
    }

    @RequestMapping(value = "/publish", produces = "application/json")
    public String publish(@RequestBody Message msg){
        // 参数校验
        if (!StringUtils.hasLength(msg.getFromUser()) ||
            !StringUtils.hasLength(msg.getToUser()) ||
            !StringUtils.hasLength(msg.getMsg())) {
            return "{\"ok\":0}";
        }
        messageList.add(msg);
        return "{\"ok\":1}";
    }

}
