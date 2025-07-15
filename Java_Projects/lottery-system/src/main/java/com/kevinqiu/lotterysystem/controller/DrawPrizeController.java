package com.kevinqiu.lotterysystem.controller;

import com.kevinqiu.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.kevinqiu.lotterysystem.common.exception.ControllerException;
import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.controller.param.DrawPrizeParam;
import com.kevinqiu.lotterysystem.controller.param.ShowWinningRecordParam;
import com.kevinqiu.lotterysystem.controller.result.WinningRecordResult;
import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;
import com.kevinqiu.lotterysystem.service.DrawPrizeService;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/winning-records/show")
    public CommonResult<List<WinningRecordResult>> showWinningRecords(@RequestBody @Validated
                                                                      ShowWinningRecordParam param) {

        List<WinningRecordDO> winnerRecordDOList = drawPrizeService.findWinnerRecords(param);
        return CommonResult.success(convertToWinnerRecordResultList(winnerRecordDOList));

    }

    private List<WinningRecordResult> convertToWinnerRecordResultList(List<WinningRecordDO> winnerRecordDOList) {

        if (null == winnerRecordDOList) {
            throw new ControllerException(ControllerErrorCodeConstants.WINNER_RECORD_LIST_IS_NULL);
        }

        return winnerRecordDOList.stream()
                .map(winningRecordDO -> {
                     WinningRecordResult winningRecordResult = new WinningRecordResult();
                     winningRecordResult.setWinnerId(winningRecordDO.getWinnerId());
                     winningRecordResult.setWinnerName(winningRecordDO.getWinnerName());
                     winningRecordResult.setPrizeName(winningRecordDO.getPrizeName());
                     winningRecordResult.setPrizeTier(ActivityPrizeTiersEnum
                             .forName(winningRecordDO.getPrizeTier()).getMessage());
                     winningRecordResult.setWinningTime(winningRecordDO.getWinningTime());
                     return winningRecordResult;
                }).toList();
    }

}
