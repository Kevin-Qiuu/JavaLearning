package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityPrizeMapper {

    void batchCreateActivityPrize(List<ActivityPrizeDO> activityPrizeDOList);

}
