package com.kevinqiu.springBookDemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevinqiu.springBookDemo.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

// Mybatis-Plus 会自动实现BaseMapper 中的方法，并且会根据 UserInfo 给定的信息推断出数据表以及列名
@Mapper
public interface UserInfoPlusMapper extends BaseMapper<UserInfo> {
}
