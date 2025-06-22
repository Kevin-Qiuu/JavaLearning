package com.kevinqiu.productservice.controller;

import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import com.kevinqiu.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/{productId}")
    public ProductInfoResult getProductInfo(@PathVariable("productId") Integer productId){
        return productService.getProductInfo(productId);
    }

}
