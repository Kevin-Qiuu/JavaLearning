package com.kevinqiu.orderservice.dao;

import com.kevinqiu.orderservice.controller.result.OrderInfoResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    @Select("select * from order_detail where id = #{orderId}")
    OrderInfoResult selectById(Integer orderId);

}
