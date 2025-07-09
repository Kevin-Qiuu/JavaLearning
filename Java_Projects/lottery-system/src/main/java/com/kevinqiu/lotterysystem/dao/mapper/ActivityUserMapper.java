package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.ActivityUserDO;
import com.kevinqiu.lotterysystem.service.enums.ActivityUserStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityUserMapper {
    void batchCreateActivityUser(List<ActivityUserDO> activityUserDOList);

    List<ActivityUserDO> selectByActivityId(Long activityId);

    List<ActivityUserDO> batchByActivityIdAndUserId(
            @Param("aId") Long activityId, @Param("uIds") List<Long> userIds);

    void updateStatusByActivityIdAndUserIds(
            @Param("aId") Long activityId,
            @Param("uIds") List<Long> userIds,
            @Param("status") ActivityUserStatusEnum targetUserStatus);
}
