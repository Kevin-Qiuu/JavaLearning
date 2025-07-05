package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityUserMapper {
    void batchCreateActivityUser(List<ActivityUserDO> activityUserDOList);

    List<ActivityUserDO> selectByActivityId(Long activityId);
}
