package com.kevinqiu.springblogdemo.pojo.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfo {

    /*
    CREATE TABLE java_blog_spring.user_info(
        `id` INT NOT NULL AUTO_INCREMENT,
        `user_name` VARCHAR ( 128 ) NOT NULL,
        `password` VARCHAR ( 128 ) NOT NULL,
        `github_url` VARCHAR ( 128 ) NULL,
        `delete_flag` TINYINT ( 4 ) NULL DEFAULT 0,
        `create_time` DATETIME DEFAULT now(),
        `update_time` DATETIME DEFAULT now() ON UPDATE now(),
        PRIMARY KEY ( id ),
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_name")
    private String username;
    private String password;
    private String githubUrl;
    private Integer deleteFlag;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
