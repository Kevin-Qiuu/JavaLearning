package com.kevinqiu.model;

import lombok.Data;

@Data
public class UserInfo {
    /*
    CREATE TABLE user_info (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `user_name` VARCHAR (128) NOT NULL,
                           `password` VARCHAR (128) NOT NULL,
                           `create_time` DATETIME DEFAULT now(),
                           `update_time` DATETIME DEFAULT now() ON UPDATE now(),
                           PRIMARY KEY (`id`)
           ) ENGINE = INNODB DEFAULT CHARACTER
           SET = utf8mb4 COMMENT = '用户表'
     */
}
