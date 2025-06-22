package com.kevinqiu.orderservice.controller.result;
import com.kevinqiu.productservice.controller.result.ProductInfoResult;
import lombok.Data;
import java.util.Date;

@Data
public class OrderInfoResult {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private ProductInfoResult productInfo;
    private Integer num;
    private Integer price;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

}
