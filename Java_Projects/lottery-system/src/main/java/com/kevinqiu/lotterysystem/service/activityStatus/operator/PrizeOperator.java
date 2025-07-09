package com.kevinqiu.lotterysystem.service.activityStatus.operator;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrizeOperator extends AbstractActivityOperator{

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public Integer sequence() {
        return 1;
    }

    @Override
    public Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        if (null == convertActivityStatusDTO){
            return false;
        }

        // 校验非空
        Long activityId = convertActivityStatusDTO.getActivityId();
        Long prizeId = convertActivityStatusDTO.getPrizeId();
        if (null == activityId || null == prizeId) {
            return false;
        }

        // 校验数据库数据非空
        ActivityPrizeDO activityPrizeDO = activityPrizeMapper
                .selectByActivityAndPrizeId(activityId, prizeId);
        if (null == activityPrizeDO) {
            return false;
        }

        // 校验奖品与目标状态之间是否保持一致
        if (activityPrizeDO.getStatus()
                .equalsIgnoreCase(convertActivityStatusDTO.getTargetUserStatus().name())) {
            return false;
        }

        return true;

    }

    @Override
    public Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO) {

        // 更新数据库
        try {
            activityPrizeMapper
                    .updatePrizeStatus(
                            convertActivityStatusDTO.getActivityId(),
                            convertActivityStatusDTO.getPrizeId(),
                            convertActivityStatusDTO.getTargetPrizeStatus()
                    );
            return true;
        } catch (Exception e) {
            log.warn("", e);
            return false;
        }

    }
}
