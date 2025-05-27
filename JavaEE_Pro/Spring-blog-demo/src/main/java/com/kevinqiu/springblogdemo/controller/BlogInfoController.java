package com.kevinqiu.springblogdemo.controller;

import com.kevinqiu.springblogdemo.common.exception.BlogException;
import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.response.BlogInfoResponse;
import com.kevinqiu.springblogdemo.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogInfoController {

    @Resource(name = "blogServiceImpl")
    private BlogService blogService;

    @RequestMapping("/getBlogList")
    public List<BlogInfoResponse> getBlogList(){
        log.info("获取博客列表");
        return blogService.getBlogList();
    }

    @RequestMapping("/getBlogInfo")
    public BlogInfoResponse getBlogInfo(Integer blogId){
        if(blogId == null){
            throw new BlogException("blogId 不可为空！");
        }
        log.info("获取博客详细信息，id{}", blogId);
        return blogService.getBlogInfo(blogId);
    }

}
