package com.kevinqiu.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBlogRequest {
    @NotNull(message = "blogId 不能为空")
    private Integer id;
    private String title;
    private String content;
    private String deleteFlag;
}
