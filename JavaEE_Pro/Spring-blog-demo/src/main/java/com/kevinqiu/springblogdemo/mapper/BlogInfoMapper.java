package com.kevinqiu.springblogdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogInfoMapper extends BaseMapper<BlogInfo> {
}
