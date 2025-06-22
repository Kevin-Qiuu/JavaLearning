package com.kevinqiu.orderservice.service;

import com.kevinqiu.orderservice.controller.result.OrderInfoResult;
import com.kevinqiu.orderservice.dao.OrderMapper;
import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;


    public OrderInfoResult getOrderInfo(Integer orderId){
        OrderInfoResult orderInfoResult = orderMapper.selectById(orderId);
        String url = "http://127.0.0.1:9090/product/" + orderInfoResult.getProductId();
        ProductInfoResult productInfoResult = restTemplate.getForObject(url, ProductInfoResult.class);
        orderInfoResult.setProductInfo(productInfoResult);
        return orderInfoResult;
    }

}
