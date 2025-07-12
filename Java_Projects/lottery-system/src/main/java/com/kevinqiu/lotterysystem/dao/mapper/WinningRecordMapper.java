package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WinningRecordMapper {

    void batchInsert(List<WinningRecordDO> winningRecordDOList);

    @Select("select * from winning_record where activity_id = #{aId}")
    List<WinningRecordDO> selectByActivityId(@Param("aId") Long activityId);

    @Select("select count(1) from winning_record where activity_id = #{aId} and prize_id = #{pId}")
    int countSelectByActivityIdAndPrizeId(@Param("aId") Long activityId, @Param("pId") Long prizeId);

    void deleteByActivityIdAndPrizeId(@Param("aId") Long activityId, @Param("pId") Long prizeId);
}
