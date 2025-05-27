package com.kevinqiu.springblogdemo.pojo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserInfoResponse {
    private Integer id;
    private String username;
    private String githubUrl;
}
