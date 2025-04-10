package com.kevinqiu.demo;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController// 为了使 SpringBoot 明确有哪些类的方法需要路径映射
@RequestMapping("/request")
public class HelloController {
    @RequestMapping("/world")
    public String hello() {
        String ret = "<h1>Hello, world</h1><br>";
        String ret_2 = "<input type=\"button\" value=\"Hello world\">";
        System.out.println(ret + ret_2);
        return ret + ret_2;
    }

    @RequestMapping("/getName")
    public String getName(@RequestParam(required = false) String name) {
        if (name == null)
            name = "KevinQiu";
        return "<h1>" + name + "</h1>";
    }

    @RequestMapping("/login")
    public String loginAccount(String userName, String passwd, Integer age){
        // 方法中有多个参数的，既可以通过 url 传参也可以通过Form 表单传参
        Person person = new Person(userName, age, passwd);
        return person.toString();
    }

    @RequestMapping("/search")
    public String search(@RequestParam("q")String query){
        return "query: " + query;
    }

    @RequestMapping("/makePerson")
    public String makePerson(@RequestBody(required = false) Person person) {
        // @RequestBody：这个注解用于接收并处理 JSON 数据，可以通过 JSON 传参
        return "<h1>" + person + "</h1>";
    }


}
