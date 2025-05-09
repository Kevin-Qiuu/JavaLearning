package com.kevinqiu.springmvc_demos.model;

import lombok.Data;

@Data
public class MessageInfo {
    private Integer id;
    private String from;
    private String to;
    private String message;
    private Integer deleteFlag;
}
