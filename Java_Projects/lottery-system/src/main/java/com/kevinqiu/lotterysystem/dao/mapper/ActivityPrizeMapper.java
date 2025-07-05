package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityPrizeMapper {

    void batchCreateActivityPrize(List<ActivityPrizeDO> activityPrizeDOList);

    @Select("select * from activity_prize where activity_id = #{activityId}")
    List<ActivityPrizeDO> selectByActivityId(Long activityId);
}
