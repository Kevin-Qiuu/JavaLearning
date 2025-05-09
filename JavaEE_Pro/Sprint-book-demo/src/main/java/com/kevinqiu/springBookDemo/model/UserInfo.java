package com.kevinqiu.springBookDemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private Integer deleteFlag;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
