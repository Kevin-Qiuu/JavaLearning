package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.BookInfo;
import com.kevinqiu.springBookDemo.model.PageRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO book_info " +
            "(book_name, author, count, price, publish, status)" +
            " values (#{bookName}, #{author}, #{count}, #{price}, #{publish},#{status})")
    void insertBook(BookInfo bookInfo);

    @Select("SELECT count(1) from book_info where status != 0")
    Integer selectBooksCount();

    List<BookInfo> selectBooksByPage(PageRequest pageRequest);
}
