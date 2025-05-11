package com.kevinqiu.springBookDemo.model;

import lombok.Data;

@Data
public class PageRequest {
    private Integer currentPage=1;
    private Integer pageSize=10;
    private Integer pageOffset=1;

    public Integer getPageOffset(){
        return (currentPage-1) * pageSize;
    }
}
