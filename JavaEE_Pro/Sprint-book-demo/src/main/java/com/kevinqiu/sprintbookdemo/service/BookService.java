package com.kevinqiu.sprintbookdemo.service;

import com.kevinqiu.sprintbookdemo.dao.BookDao;
import com.kevinqiu.sprintbookdemo.model.BookInfo;

import java.util.List;

public class BookService {
    public List<BookInfo> getBookList(){
        BookDao bookDao = new BookDao();
        return bookDao.mockBookData();
    }
}
