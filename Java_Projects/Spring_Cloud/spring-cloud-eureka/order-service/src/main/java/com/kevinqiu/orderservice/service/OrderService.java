package com.kevinqiu.orderservice.service;

import com.kevinqiu.orderservice.controller.result.OrderInfoResult;
import com.kevinqiu.orderservice.dao.OrderMapper;
import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    private List<ServiceInstance> instances;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    @PostConstruct
    private void init() {
        instances = discoveryClient.getInstances("product-service");
    }

//    public OrderInfoResult getOrderInfo(Integer orderId){
//        OrderInfoResult orderInfoResult = orderMapper.selectById(orderId);
//        String url = "http://127.0.0.1:9090/product/" + orderInfoResult.getProductId();
//        ProductInfoResult productInfoResult = restTemplate.getForObject(url, ProductInfoResult.class);
//        orderInfoResult.setProductInfo(productInfoResult);
//        return orderInfoResult;
//    }

//    public OrderInfoResult getOrderInfo(Integer orderId) {
//        OrderInfoResult orderInfoResult = orderMapper.selectById(orderId);
//        // 轮询调用服务端
//        int increment = atomicInteger.getAndIncrement();
//        EurekaServiceInstance instance = (EurekaServiceInstance)instances.get(increment % instances.size());
//        logger.info("请求实例：" + instance.getInstanceId());
//        String url =instance.getUri() + "/product/" + + orderInfoResult.getProductId();
//        ProductInfoResult productInfoResult = restTemplate.getForObject(url, ProductInfoResult.class);
//        orderInfoResult.setProductInfo(productInfoResult);
//        return orderInfoResult;
//    }

    public OrderInfoResult getOrderInfo(Integer orderId) {
        OrderInfoResult orderInfoResult = orderMapper.selectById(orderId);
        // 轮询调用服务端
        int increment = atomicInteger.getAndIncrement();
        EurekaServiceInstance instance = (EurekaServiceInstance) instances.get(increment % instances.size());
        logger.info("请求实例：" + instance.getInstanceId());
        String url = instance.getUri() + "/product/" + +orderInfoResult.getProductId();
        ProductInfoResult productInfoResult = restTemplate.getForObject(url, ProductInfoResult.class);
        orderInfoResult.setProductInfo(productInfoResult);
        return orderInfoResult;
    }


}
