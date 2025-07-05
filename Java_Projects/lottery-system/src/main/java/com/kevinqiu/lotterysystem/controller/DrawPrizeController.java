package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DrawPrizeController {

    @Autowired
    private DrawPrizeService drawPrizeService;

    @RequestMapping("/draw-prize")
    public CommonResult<Boolean> drawPrize(
            @Validated @RequestBody DrawPrizeParam param) {
        log.info("drawPrize -> DrawPrizeParam:{}", param);
        drawPrizeService.drawPrize(param);
        return CommonResult.success(Boolean.TRUE);
    }

}
