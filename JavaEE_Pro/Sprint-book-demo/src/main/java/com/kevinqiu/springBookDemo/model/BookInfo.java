package com.kevinqiu.springBookDemo.model;

import lombok.Data;

@Data
public class BookInfo {
    private String bookId;
    private String bookName;
    private String authorName;
    private Integer bookCount;
    private Integer bookPrice;
    private String bookPublish;
    private Integer bookStatus;
    private String bookStatusCN;
}
