package com.kevinqiu.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        String ret = "<h1>Hello, world</h1><br>";
        String ret_2 = "<input type=\"button\" value=\"Hello world\">";
        System.out.println(ret + ret_2);
        return ret + ret_2;
    }
}
