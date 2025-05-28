package com.kevinqiu.springblogdemo.controller;

import com.kevinqiu.springblogdemo.common.exception.BlogException;
import com.kevinqiu.springblogdemo.pojo.dataobject.BlogInfo;
import com.kevinqiu.springblogdemo.pojo.request.AddBlogInfoRequest;
import com.kevinqiu.springblogdemo.pojo.request.UpdateBlogRequest;
import com.kevinqiu.springblogdemo.pojo.response.BlogDetailResponse;
import com.kevinqiu.springblogdemo.pojo.response.BlogInfoResponse;
import com.kevinqiu.springblogdemo.pojo.response.UserInfoResponse;
import com.kevinqiu.springblogdemo.service.BlogService;
import com.kevinqiu.springblogdemo.service.UserService;
import com.kevinqiu.springblogdemo.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogInfoController {

    @Resource(name = "blogServiceImpl")
    private BlogService blogService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @RequestMapping("/getBlogList")
    public List<BlogInfoResponse> getBlogList(){
        log.info("获取博客列表");
        return blogService.getBlogList();
    }

    @RequestMapping("/getBlogDetail")
    public BlogDetailResponse getBlogDetail(Integer blogId){
        log.info("获取博客详细信息，id: {}", blogId);
        if(blogId == null){
            throw new BlogException("blogId 不可为空！");
        }
        return blogService.getBlogDetail(blogId);
    }

    @RequestMapping("/getAuthorInfo")
    public UserInfoResponse getAuthorInfo(String blogId){
        log.info("获取作者信息，blogId: {}", blogId);
        return userService.getUserByBlogId(blogId);
    }

    @RequestMapping("/addBlogInfo")
    public Boolean addBlogInfo(@Validated @RequestBody AddBlogInfoRequest addBlogInfoRequest){
        log.info("添加新的博客信息，addBlogInfo: {}", addBlogInfoRequest);
        return blogService.addBlogInfo(addBlogInfoRequest);
    }

    @RequestMapping("/updateBlogInfo")
    public Integer updateBlogInfo(@RequestBody UpdateBlogRequest updateBlogRequest){
        log.info("更新博客信息，updateBlogRequest: {}", updateBlogRequest);
        return blogService.updateBlogInfo(updateBlogRequest);
    }

    @RequestMapping("/deleteBlogInfo")
    public Integer deleteBlogInfo(@NotBlank String blogId){
        log.info("删除博客信息，blogId: {}", blogId);
        return blogService.deleteBlogInfo(blogId);
    }

}
