package com.kevinqiu.springiocdemo.printLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/log")
@RestController
public class LoggerController {
    // getLogger 需要跟一个参数，来表示日志是在哪一个类下产生的，便于后期追踪
    private static final Logger logger = LoggerFactory.getLogger(LoggerController.class);
    @RequestMapping("/getLog")
    public String printLog(){
        // log 的等级从 error -> warn -> info -> debug -> trace，依次降低
        logger.error("This is error log.");
        logger.warn("This is warn log.");
        logger.info("This is info log.");
        logger.debug("This is debug log.");
        logger.trace("This is trace log.");
        return "Logs have been printed in the local console.";
    }
}
