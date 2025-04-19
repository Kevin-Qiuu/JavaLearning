package com.kevinqiu.sprintbookdemo.dao;

import com.kevinqiu.sprintbookdemo.model.BookInfo;

import java.util.ArrayList;
import java.util.List;

public class BookDao {
    public List<BookInfo> mockBookData() {
        List<BookInfo> bookInfoList = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookId(String.valueOf(i));
            bookInfo.setBookName("book " + i);
            bookInfo.setAuthorName("author " + i);
            bookInfo.setBookCount(i * 20);
            bookInfo.setBookPrice(i * 10);
            bookInfo.setBookPublish("publish " + i);
            bookInfo.setBookStatus((i % 5 == 0) ? 0 : 1);
            bookInfo.setBookStatusCN(bookInfo.getBookStatus() == 1 ? "可借阅" : "不可借阅");
            bookInfoList.add(bookInfo);
        }
        return bookInfoList;
    }
}
