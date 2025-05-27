package com.kevinqiu.springblogdemo.pojo.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.jdi.request.StepRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogInfo {

    /*
    CREATE TABLE java_blog_spring.blog_info (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NULL,
  `content` TEXT NULL,
  `user_id` INT(11) NULL,
  `delete_flag` TINYINT(4) NULL DEFAULT 0,
  `create_time` DATETIME DEFAULT now(),
  `update_time` DATETIME DEFAULT now() ON UPDATE now(),
  PRIMARY KEY (id))
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private String deleteFlag;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;

}
