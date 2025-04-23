package com.kevinqiu.springiocdemo.printLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/aLog")
@RestController
@Slf4j
public class UseAnnotationLoggerController {
    // @Slf4j 会自动帮我们创建一个 Logger 对象
    /*
    @Generated
    private static final Logger log = LoggerFactory.getLogger(UseAnnotationLoggerController.class);
     */
    @RequestMapping("/getLog")
    public String printLog(){
        log.error("This is error log.");
        log.warn("This is warn log.");
        log.info("This is info log.");
        log.debug("This is debug log.");
        log.trace("This is trace log.");
        return "Logs have been printed in the local console.";
    }
}
