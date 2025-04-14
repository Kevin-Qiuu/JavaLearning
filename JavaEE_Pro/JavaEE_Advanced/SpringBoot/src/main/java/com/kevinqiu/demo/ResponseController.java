package com.kevinqiu.demo;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/response")
public class ResponseController {

    // 通过 HttpServletResponse 设置响应报文的头信息
    // SpringMVC 会自动给 HttpServletResponse 赋值
    @RequestMapping(value = "setHeaders")
    @ResponseBody
    public String setHeader(HttpServletResponse response){
        response.setHeader("Content-Type", "application/json");
        return "{\"hello\":\"world\"}";
    }

    @RequestMapping("getHtml")
    @ResponseBody
    // @ResponseBody 作用在类上表示类的所有成员方法都是返回的数据，作用在方法上，则这个方法返回的是数据
    // 加了 @ResponseBody 注解，SpringMVC 只会把返回的数据看做成普通的数据而不是目录下的资源文件
    public String getHtml(){
        return "/TestDemo.html";
    }

    @RequestMapping("/getPerson")
    // 这会返回一个 JSON 格式，描述的是 Person
    // SpringMVC 会根据返回类型自动设置 Content-type，如果返回的是对象则返回JSON格式数据
    public Person getPerson(){
        return new Person("KevinQiu", 23, "123456999");
    }

    @RequestMapping("/setStatus")
    public String setStatus(HttpServletResponse response){
        response.setStatus(404);
        return "/TestDemo.html";
    }
}
