package com.kevinqiu.springblogdemo.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogInfoResponse {
    private Integer id;
    private String title;
    private String content;
    private String desc = "";
    private Integer userId;
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime createTime;

    public void setDesc(String content) {
        if (content.length() < 40) {
            this.desc = content;
            return;
        }
        this.desc = content.substring(0, 40) + "...";
    }
}
