package com.kevinqiu.sprintbookdemo.controller;

import com.kevinqiu.sprintbookdemo.model.BookInfo;
import com.kevinqiu.sprintbookdemo.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @RequestMapping("/getBooks")
    public List<BookInfo> getBooks(){
        BookService bookService = new BookService();
        return bookService.getBookList();
    }
}
