package com.kevinqiu.springBookDemo.service;

import com.kevinqiu.springBookDemo.mapper.BookMapper;
import com.kevinqiu.springBookDemo.model.BookInfo;
import com.kevinqiu.springBookDemo.model.PageRequest;
import com.kevinqiu.springBookDemo.model.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public void addBook(BookInfo bookInfo) {
        bookMapper.insertBook(bookInfo);
    }

    public PageResponse<BookInfo> getBooks(PageRequest pageRequest) {
        Integer totalBookNum = bookMapper.selectBooksCount();
        List<BookInfo> bookInfoList = bookMapper.selectBooksByPage(pageRequest);
        for (BookInfo bookInfo : bookInfoList) {
            bookInfo.setStatusCN();
//            System.out.println(bookInfo);
        }
        return new PageResponse<>(totalBookNum, bookInfoList, pageRequest);
    }
}
