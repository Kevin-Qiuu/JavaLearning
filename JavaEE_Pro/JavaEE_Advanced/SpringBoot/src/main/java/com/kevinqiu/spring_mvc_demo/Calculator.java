package com.kevinqiu.spring_mvc_demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/calculator")
public class Calculator {
    @RequestMapping("/index")
    public String showCalHtml(){
        return "/calc.html";
    }

    @RequestMapping("/sum")
    @ResponseBody
    public String sum(Integer num1, Integer num2) {
        if (num1 == null || num2 == null) {
            return "Please input again";
        }
        Integer sum = num1 + num2;
        return "The result is " + sum;
    }

}
