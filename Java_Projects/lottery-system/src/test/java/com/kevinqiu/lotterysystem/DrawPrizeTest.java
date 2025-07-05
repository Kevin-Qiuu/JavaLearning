package com.kevinqiu.lotterysystem;

import cn.hutool.core.date.DateTime;
import com.kevinqiu.lotterysystem.controller.DrawPrizeController;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class DrawPrizeTest {

    @Autowired
    private DrawPrizeService drawPrizeService;

    @Test
    void drawPrize() {
        DrawPrizeParam param = new DrawPrizeParam();
        param.setPrizeId(1L);
        param.setActivityId(1L);
        param.setPrizeId(1L);
        param.setWinningTime(DateTime.now());
        List<DrawPrizeParam.Winner> winnerList = new LinkedList<>();
        DrawPrizeParam.Winner winner = new DrawPrizeParam.Winner();
        winner.setUserId(1L);
        winner.setUserName("xxxxx");
        winnerList.add(winner);
        param.setWinnerList(winnerList);
        drawPrizeService.drawPrize(param);
    }

}
