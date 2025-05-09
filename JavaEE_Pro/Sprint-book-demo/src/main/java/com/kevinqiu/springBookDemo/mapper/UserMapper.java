package com.kevinqiu.springBookDemo.mapper;

import com.kevinqiu.springBookDemo.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserInfo selectUserByUsername(String username);
}
