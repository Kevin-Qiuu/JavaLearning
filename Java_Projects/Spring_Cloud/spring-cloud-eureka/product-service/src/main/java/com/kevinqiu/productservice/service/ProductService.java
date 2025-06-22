package com.kevinqiu.productservice.service;

import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import com.kevinqiu.productservice.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public ProductInfoResult getProductInfo(Integer productId){
        return productMapper.selectById(productId);
    }

}
