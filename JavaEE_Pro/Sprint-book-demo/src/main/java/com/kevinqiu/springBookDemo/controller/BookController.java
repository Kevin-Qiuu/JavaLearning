package com.kevinqiu.springBookDemo.controller;

import com.kevinqiu.springBookDemo.model.BookInfo;
import com.kevinqiu.springBookDemo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping("/addBook")
    public String addBook(@RequestBody BookInfo bookInfo){
        logger.info("添加图书：{}", bookInfo);
        // 校验 bookInfo 的属性
        if (//!StringUtils.hasLength(bookInfo.getId())
                !StringUtils.hasLength(bookInfo.getBookName())
                || !StringUtils.hasLength(bookInfo.getAuthor())
                || !StringUtils.hasLength(bookInfo.getPublish())
                || bookInfo.getCount() == null
                || bookInfo.getPrice() == null
                || bookInfo.getStatus() == null) {
            return "输入参数非法，请检查输入参数！";
        }


        try{
            bookService.addBook(bookInfo);
            return "";
        } catch (Exception e){
            logger.error("添加图书失败：e", e);
            return "添加图书失败，联系 KevinQiu。";
        }

    }
}
