package com.kevinqiu.springBookDemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {



    @RequestMapping("/t1")
    public Integer t1(){
        int a = 10 / 0;
        return 1;
    }

    @RequestMapping("/t2")
    public Boolean t2(){
        return true;
    }

    @RequestMapping("/t3")
    public String t3(){

        return "test";  // Spring 没有设置String 的 Convertor 来进行结果的转化，最终会因为函数调用参数不匹配报错
    }

}
