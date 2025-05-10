package com.kevinqiu.springBookDemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookInfo {
    private String id;
    private String bookName;
    private String author;
    private Integer count;
    private Integer price;
    private String publish;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
