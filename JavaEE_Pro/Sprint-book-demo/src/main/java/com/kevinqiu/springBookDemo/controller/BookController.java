package com.kevinqiu.springBookDemo.controller;

import com.kevinqiu.springBookDemo.model.BookInfo;
import com.kevinqiu.springBookDemo.model.PageRequest;
import com.kevinqiu.springBookDemo.model.PageResponse;
import com.kevinqiu.springBookDemo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/addBook")
    public String addBook(@RequestBody BookInfo bookInfo) {
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


        try {
            bookService.addBook(bookInfo);
            return "";
        } catch (Exception e) {
            logger.error("添加图书失败：e", e);
            return "添加图书失败，联系 KevinQiu。";
        }

    }

    @RequestMapping("/getBooks")
    public PageResponse<BookInfo> getBooks(PageRequest pageRequest) {
        logger.info("分页查询图书列表，pageRequest{}", pageRequest);
        try {
            return bookService.getBooks(pageRequest);
        } catch (Exception e) {
            logger.error("翻页查询图书失败：e", e);
            return null;
        }
    }

    @RequestMapping(value = "/getBookById", produces = "application/json")
    public BookInfo getBookById(Integer bookId) {
        logger.info("根据 Id 查询图书信息，id{}", bookId);
        try{
            return bookService.selectBookById(bookId);
        } catch (Exception e){
            logger.error("根据 Id 查询图书信息失败，e", e);
            return null;
        }
    }

    @RequestMapping("/updateBook")
    public String updateBook(BookInfo bookInfo) {
        logger.info("更新图书信息，bookInfo{}", bookInfo);
        try {
            bookService.updateBook(bookInfo);
            return "";
        } catch (Exception e) {
            logger.error("更新图书信息失败，e", e);
            return "更新失败，请联系 KevinQiu。";
        }
    }

    @RequestMapping("/deleteBooks")
    public String deleteBooks(List<Integer> ids) {
        logger.info("批量删除图书，ids{}", ids);
        try {
            bookService.deleteBooks(ids);
            return "";
        } catch (Exception e) {
            logger.info("图书删除失败，e", e);
            return "图书删除失败，联系 KevinQiu。";
        }
    }


}
