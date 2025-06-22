package com.kevinqiu.productservice.dao;

import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {

    @Select("select * from product_detail where id = #{productId}")
    ProductInfoResult selectById(Integer productId);

}
