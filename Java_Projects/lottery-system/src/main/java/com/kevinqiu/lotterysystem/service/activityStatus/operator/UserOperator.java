package com.kevinqiu.lotterysystem.service.activityStatus.operator;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityUserDO;
import com.kevinqiu.lotterysystem.dao.mapper.ActivityUserMapper;
import com.kevinqiu.lotterysystem.service.dto.ConvertActivityStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserOperator extends AbstractActivityOperator {

    @Autowired
    private ActivityUserMapper activityUserMapper;

    @Override
    public Integer sequence() {
        return 1;
    }

    @Override
    public Boolean needConvert(ConvertActivityStatusDTO convertActivityStatusDTO) {

        if (null == convertActivityStatusDTO) {
            return false;
        }

        // 判断非空
        List<ActivityUserDO> activityUserDOList = activityUserMapper
                .batchByActivityIdAndUserId(
                        convertActivityStatusDTO.getActivityId(),
                        convertActivityStatusDTO.getUserIds());

        // 判断数据库非空
        if (null == activityUserDOList) {
            return false;
        }

        // 判断是否存在与目标状态一致的
        for (ActivityUserDO activityUserDO : activityUserDOList) {
            if (activityUserDO.getStatus()
                    .equalsIgnoreCase(convertActivityStatusDTO.getTargetUserStatus().name())) {
                return false;
            }
        }

        return true;

    }

    @Override
    public Boolean convert(ConvertActivityStatusDTO convertActivityStatusDTO) {

        try {
            activityUserMapper.updateStatusByActivityIdAndUserIds(
                    convertActivityStatusDTO.getActivityId(),
                    convertActivityStatusDTO.getUserIds(),
                    convertActivityStatusDTO.getTargetUserStatus()
            );
            return true;
        } catch (Exception e) {
            log.warn("", e);
            return false;
        }

    }
}
