package com.kevinqiu.springblogdemo.service;

import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.kevinqiu.springblogdemo.pojo.request.UpdateBlogRequest;
import com.kevinqiu.springblogdemo.pojo.response.BlogDetailResponse;
import com.kevinqiu.springblogdemo.pojo.response.BlogInfoResponse;

import java.util.List;

public interface BlogService {
    List<BlogInfoResponse> getBlogList();
    BlogDetailResponse getBlogDetail(Integer id);
    Boolean addBlogInfo(AddBlogInfoRequest addBlogInfoRequest);
    Integer deleteBlogInfo(String blogId);
    Integer updateBlogInfo(UpdateBlogRequest updateBlogRequest);

}
