package com.kevinqiu.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kevinqiu.springblogdemo.mapper.BlogInfoMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.response.BlogInfoResponse;
import com.kevinqiu.springblogdemo.service.BlogService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource(name = "blogInfoMapper")
    private BlogInfoMapper blogInfoMapper;

    @Override
    public List<BlogInfoResponse> getBlogList() {
        List<BlogInfo> blogInfos = blogInfoMapper.selectList(
                new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getDeleteFlag, 0)
        );

        return blogInfos.stream().map((BlogInfo blogInfo) -> {
            BlogInfoResponse blogInfoResponse = new BlogInfoResponse();
            BeanUtils.copyProperties(blogInfo, blogInfoResponse);
            blogInfoResponse.setDesc(blogInfoResponse.getContent());
            blogInfoResponse.setContent("");
            return blogInfoResponse;
        }).toList();
    }

    @Override
    public BlogInfoResponse getBlogInfo(Integer id) {
        BlogInfoResponse blogInfoResponse = new BlogInfoResponse();
        BlogInfo blogInfo = blogInfoMapper.selectOne(
                new LambdaQueryWrapper<BlogInfo>()
                        .eq(BlogInfo::getDeleteFlag, 0)
                        .eq(BlogInfo::getId, id)
        );
        BeanUtils.copyProperties(blogInfo, blogInfoResponse);
        return blogInfoResponse;
    }
}
