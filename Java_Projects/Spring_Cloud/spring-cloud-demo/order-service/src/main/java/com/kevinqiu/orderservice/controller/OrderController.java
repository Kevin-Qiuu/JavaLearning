package com.kevinqiu.orderservice.controller;

import com.kevinqiu.orderservice.controller.result.OrderInfoResult;
import com.kevinqiu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kevinqiu.productservice.controller.result.ProductInfoResult;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @RequestMapping("/{orderId}")
    public OrderInfoResult getOrderInfo(@PathVariable("orderId") Integer orderId){
        return orderService.getOrderInfo(orderId);
    }

}
