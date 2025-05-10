package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.BookInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface BookMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO book_info " +
            "(book_name, author, count, price, publish, status)" +
            " values (#{bookName}, #{author}, #{count}, #{price}, #{publish},#{status})")
    void insertBook(BookInfo bookInfo);
}
