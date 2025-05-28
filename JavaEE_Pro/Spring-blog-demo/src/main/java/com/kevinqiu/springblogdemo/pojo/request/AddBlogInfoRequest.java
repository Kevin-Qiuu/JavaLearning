package com.kevinqiu.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddBlogInfoRequest {
    @NotNull(message = "userId 不能为空")
    private Integer userId;
    @NotNull(message = "title 不能为空")
    private String title;
    @NotNull(message = "content 不能为空")
    private String content;
}
