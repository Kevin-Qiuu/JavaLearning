package com.kevinqiu.mybatisdemo.mapper;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private Integer gender;
    private String phone;
    Integer deleteFlag;
//    String create_time;
//    String update_time;
}
