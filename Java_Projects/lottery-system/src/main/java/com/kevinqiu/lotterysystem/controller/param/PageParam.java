package com.kevinqiu.lotterysystem.controller.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    @NotNull(message = "当前页为空！")
    Integer currentPage;

    /**
     * 当前页数量
     */
    @NotNull(message = "当前页数量为空！")
    Integer pageSize;

    /**
     * 计算偏移量，用于传递 SQL 语句中需要跳过的记录数
     * @return
     */
    public Integer offset() {
        return (currentPage - 1) * pageSize;
    }

}
