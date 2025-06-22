package com.kevinqiu.productservice.controller;

import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import com.kevinqiu.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping("/{productId}")
    public ProductInfoResult getProductInfo(@PathVariable("productId") Integer productId){
        logger.info("查询商品信息，商品 Id：{}", productId.toString());
        return productService.getProductInfo(productId);
    }

}
