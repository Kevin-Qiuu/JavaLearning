package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;

public interface DrawPrizeService {

    /**
     * 生产中奖消息
     *
     * @param param
     */
    void drawPrize(DrawPrizeParam param);

    Boolean checkDrawPrizeParam(DrawPrizeParam param);
}
