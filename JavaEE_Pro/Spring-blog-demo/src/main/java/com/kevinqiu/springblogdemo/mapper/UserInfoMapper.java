package com.kevinqiu.springblogdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
