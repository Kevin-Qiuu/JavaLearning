package com.kevinqiu.lotterysystem.service.activityStatus.operator;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityDO;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityMapper;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityPrizeMapper;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.kevinqiu.lotterysystem.service.enums.ActivityStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ActivityOperator extends AbstractActivityOperator {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;

    @Override
    public Integer sequence() {
        return 2;
    }

    @Override
    public Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO) {
        if (null == convertActivityStatusDTO) {
            return false;
        }

        Long activityId = convertActivityStatusDTO.getActivityId();
        ActivityStatusEnum targetActivityStatus = convertActivityStatusDTO.getTargetActivityStatus();
        if (null == activityId
                || null == targetActivityStatus) {
            return false;
        }

        ActivityDO activityDO = activityMapper.selectById(activityId);
        if (null == activityDO) {
            return false;
        }

        // 判断活动是否已经完成了目标状态转换
        if (targetActivityStatus.equals(ActivityStatusEnum.forName(activityDO.getStatus()))) {
            return false;
        }

        // 判断奖品是否已经全部抽完，判断当前活动处于 INIT 状态的奖品数量，如果大于零则存在未抽完奖品
        int count = activityPrizeMapper.countPrizeByAIdAndStatus(activityId, ActivityPrizeStatusEnum.INIT.name());
        if (count > 0) {
            return false;
        }

        // todo
        // 后续再做更严格的校验，判断人员是否已经抽取完了

        return true;
    }

    @Override
    public Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO) {

        try {
            activityMapper.updateStatus(convertActivityStatusDTO.getActivityId(),
                    convertActivityStatusDTO.getTargetActivityStatus().name());
            return true;
        } catch (Exception e) {
            log.warn("", e);
            return false;
        }

    }
}
