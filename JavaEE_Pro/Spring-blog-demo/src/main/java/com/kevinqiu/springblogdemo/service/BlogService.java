package com.kevinqiu.springblogdemo.service;

import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.response.BlogInfoResponse;

import java.util.List;

public interface BlogService {
    List<BlogInfoResponse> getBlogList();
    BlogInfoResponse getBlogInfo(Integer id);
}
