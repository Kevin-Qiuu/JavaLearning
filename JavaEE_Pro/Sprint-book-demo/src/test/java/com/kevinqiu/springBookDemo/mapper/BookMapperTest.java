package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.BookInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookMapperTest {
    @Autowired
    BookMapper bookMapper;

    @Test
    void insertBook() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookName("hhhh");
        bookInfo.setAuthor("qiu");
        bookInfo.setCount(20);
        bookInfo.setPrice(100);
        bookInfo.setPublish("SWJTU");
        bookInfo.setStatus(1);
        bookMapper.insertBook(bookInfo);
    }
}