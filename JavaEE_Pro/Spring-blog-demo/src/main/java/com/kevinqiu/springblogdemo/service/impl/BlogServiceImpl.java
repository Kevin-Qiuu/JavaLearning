package com.kevinqiu.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kevinqiu.springblogdemo.common.exception.BlogException;
import com.kevinqiu.springblogdemo.common.utils.BeansTransfer;
import com.kevinqiu.springblogdemo.mapper.BlogInfoMapper;
import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.kevinqiu.springblogdemo.pojo.request.UpdateBlogRequest;
import com.kevinqiu.springblogdemo.pojo.response.BlogDetailResponse;
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
            blogInfoResponse.setDesc(blogInfo.getContent());
            return blogInfoResponse;
        }).toList();
    }

    @Override
    public BlogDetailResponse getBlogDetail(Integer blogId) {
        BlogInfo blogInfo = null;
        try {
            blogInfo = blogInfoMapper.selectOne(
                    new LambdaQueryWrapper<BlogInfo>()
                            .eq(BlogInfo::getDeleteFlag, 0)
                            .eq(BlogInfo::getId, blogId)
            );
        } catch (Exception e) {
            throw new BlogException("Blog 查询失败，blogId: " + blogId);
        }
        return BeansTransfer.trans2BlogDetail(blogInfo);
    }

    @Override
    public Boolean addBlogInfo(AddBlogInfoRequest addBlogInfoRequest) {
        if (addBlogInfoRequest == null) {
            throw new BlogException("Blog 添加失败！");
        }
        BlogInfo addblogInfo = new BlogInfo();
        BeanUtils.copyProperties(addBlogInfoRequest, addblogInfo);
        int insert = blogInfoMapper.insert(addblogInfo);
        return insert == 1;
    }

    @Override
    public Integer updateBlogInfo(UpdateBlogRequest updateBlogRequest) {
        BlogInfo blogInfo = new BlogInfo();
        BeanUtils.copyProperties(updateBlogRequest, blogInfo);
        int ret = 0;
        try{
            ret = blogInfoMapper.updateById(blogInfo);
        } catch (Exception e){
            throw new BlogException("Blog 更新失败");
        }
        return ret;
    }

    @Override
    public Integer deleteBlogInfo(String blogId) {
        UpdateBlogRequest updateBlogRequest = new UpdateBlogRequest();
        updateBlogRequest.setId(Integer.valueOf(blogId));
        updateBlogRequest.setDeleteFlag("1");
        return updateBlogInfo(updateBlogRequest);
    }
}
