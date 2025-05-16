package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.BookInfo;
import com.kevinqiu.springBookDemo.model.PageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
        bookInfo.setPrice(100.0);
        bookInfo.setPublish("SWJTU");
        bookInfo.setStatus(1);
        bookMapper.insertBook(bookInfo);
    }

    @Test
    void selectBooksCount() {
        Integer i = bookMapper.selectBooksCount();
        System.out.println("书的全部本数为：" + i);
    }

    @Test
    void selectBooksByPage() {
        PageRequest pageRequest = new PageRequest();
//        pageRequest.setCurrentPage(2);
        bookMapper.selectBooksByPage(pageRequest).stream().forEach(System.out::println);
    }

    @Test
    void deleteBooks() {
        List<Integer> ids = new ArrayList<>();
        ids.add(6);
        ids.add(7);
//        ids.add(8);
        bookMapper.deleteBooks(ids);
    }

    @Test
    void updateBook() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId("8");
        bookInfo.setBookName("路过的风");
        bookInfo.setAuthor("qiu");
        bookInfo.setCount(20);
        bookInfo.setPrice(100.0);
        bookInfo.setPublish("SWJTU");
        bookInfo.setStatus(1);
        bookMapper.updateBook(bookInfo);
    }

    @Test
    void selectBookById() {
        System.out.println(bookMapper.selectBookById(-1));
    }
}