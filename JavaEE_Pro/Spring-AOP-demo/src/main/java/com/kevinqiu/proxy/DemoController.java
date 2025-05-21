package com.kevinqiu.proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoController implements ObjectController {
    @Override
    public String printResult() {
        log.info("testDemo01: 开始执行");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("testDemo01: 执行完毕");
        return "success testDemo01";
    }
}
