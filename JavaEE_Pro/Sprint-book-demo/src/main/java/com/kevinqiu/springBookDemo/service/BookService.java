package com.kevinqiu.springBookDemo.service;

import com.kevinqiu.springBookDemo.mapper.BookMapper;
import com.kevinqiu.springBookDemo.model.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public void addBook(BookInfo bookInfo) {
        bookMapper.insertBook(bookInfo);
    }
}
