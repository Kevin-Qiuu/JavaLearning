package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.WinningRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WinnerRecordMapper {

    void batchInsert(List<WinningRecordDO> winningRecordDOList);

    @Select("select * from winning_record where activity_id = #{aId}")
    List<WinningRecordDO> selectByActivityId(@Param("aId") Long activityId);

}
