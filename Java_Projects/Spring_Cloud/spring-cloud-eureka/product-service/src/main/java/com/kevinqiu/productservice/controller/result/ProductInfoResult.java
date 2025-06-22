package com.kevinqiu.productservice.controller.result;

import lombok.Data;

import java.util.Date;

@Data
public class ProductInfoResult {

    private Integer id;
    private String productName;
    private Integer productPrice;
    private Integer state;
    private Date createTime;
    private Date updateTime;

}
