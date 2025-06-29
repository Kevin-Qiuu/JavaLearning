package com.kevinqiu.langchain4jdemo.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CalculatorTools {

    @Tool
    double time(double a, double b){
        log.info("调用乘法运算，a:{}, b:{}", a, b);
        return a * b;
    }

    @Tool
    double squareRoot(double x){
        log.info("调用平方根运算，x:{}", x);
        return Math.sqrt(x);
    }

}
