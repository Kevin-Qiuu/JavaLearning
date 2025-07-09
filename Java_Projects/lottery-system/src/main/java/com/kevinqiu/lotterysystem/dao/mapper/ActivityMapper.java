package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @Insert("insert into activity (activity_name, description, status)" +
            " values (#{activityName}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void createActivity(ActivityDO activityDO);

    @Select("select count(1) from activity")
    int count();

    @Select("select * from activity order by gmt_create desc limit #{offset}, #{pageSize}")
    List<ActivityDO> selectActivityPageList(Integer offset, Integer pageSize);

    @Select("select * from activity where id = #{activityId}")
    ActivityDO selectById(Long activityId);

    @Update("update activity set status = #{statusName} where id = #{activityId}")
    void updateStatus(Long activityId, String statusName);
}
