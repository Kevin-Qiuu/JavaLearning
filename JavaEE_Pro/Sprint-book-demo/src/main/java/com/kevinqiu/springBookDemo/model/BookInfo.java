package com.kevinqiu.springBookDemo.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookInfo {
    private String id;
    private String bookName;
    private String author;
    private Integer count;
    private Double price;
    private String publish;
    private Integer status;
    private String statusCN;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public void setStatusCN(){
        this.statusCN = BookStatus.getDescByCode(getStatus()).getDesc();
    }
}
