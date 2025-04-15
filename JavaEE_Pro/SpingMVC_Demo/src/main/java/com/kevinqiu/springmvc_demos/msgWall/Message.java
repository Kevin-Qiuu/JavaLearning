package com.kevinqiu.springmvc_demos.msgWall;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Data 注解自动设置类的构造器、提取器、构造方法等
@Data
public class Message {
    private String fromUser;
    private String toUser;
    private String msg;

//    public String getFromUser() {
//        return fromUser;
//    }
//
//    public void setFromUser(String fromUser) {
//        this.fromUser = fromUser;
//    }
//
//    public String getToUser() {
//        return toUser;
//    }
//
//    public void setToUser(String toUser) {
//        this.toUser = toUser;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
}
