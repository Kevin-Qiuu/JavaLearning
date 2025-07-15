package com.kevinqiu.lotterysystem.service;

import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.controller.param.ShowWinningRecordParam;
import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;

import java.util.List;

public interface DrawPrizeService {

    /**
     * 生产中奖消息
     *
     * @param param
     */
    void drawPrize(DrawPrizeParam param);

    /**
     * 校验中奖参数
     *
     * @param param
     */
    Boolean checkDrawPrizeParam(DrawPrizeParam param);

    /**
     * 保存中奖记录
     *
     * @param param
     */
    List<WinningRecordDO> saveWinnerRecords(DrawPrizeParam param);

    /**
     * 删除中奖记录
     *
     * @param activityId
     * @param prizeId
     */
    void deleteWinnerRecords(Long activityId, Long prizeId);


    /**
     * 查询中奖记录
     *
     * @param param
     * @return
     */
    List<WinningRecordDO> findWinnerRecords(ShowWinningRecordParam param);


}
