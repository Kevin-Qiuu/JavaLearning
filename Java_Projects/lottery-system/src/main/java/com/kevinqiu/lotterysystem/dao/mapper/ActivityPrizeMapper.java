package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.kevinqiu.lotterysystem.dao.dataobject.PrizeInfoDO;
import com.kevinqiu.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ActivityPrizeMapper {

    void batchCreateActivityPrize(List<ActivityPrizeDO> activityPrizeDOList);

    @Select("select * from activity_prize where activity_id = #{activityId}")
    List<ActivityPrizeDO> selectByActivityId(Long activityId);

    @Select("select * from activity_prize where activity_id = #{aId} and prize_id = #{pId}")
    ActivityPrizeDO selectByActivityAndPrizeId(@Param("aId") Long activityId, @Param("pId") Long prizeId);

    @Select("select count(1) from activity_prize where activity_id = #{aId} and status = #{status}")
    int countPrizeByAIdAndStatus(@Param("aId") Long activityId, @Param("status") String activityPrizeStatus);

    @Update("update activity_prize set status = #{tPS} where activity_id = #{aId} and prize_id = #{pId}")
    void updatePrizeStatus(@Param("aId") Long activityId,
                           @Param("pId") Long prizeId,
                           @Param("tPS") ActivityPrizeStatusEnum targetPrizeStatus);
}
