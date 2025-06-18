package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.PrizeInfoDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrizeMapper {
    @Insert("insert into prize (name, description, price, image_url)" +
            " values (#{name}, #{description}, #{price}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void createPrize(PrizeInfoDO prizeInfoDO);

    @Select("select count(1) from prize")
    Integer selectPrizeCount();

    // 从第 #{offset} 条数据开始，遍历 #{pageSize} 条数据
    @Select("select * from prize order by id desc limit #{offset}, #{pageSize}")
    List<PrizeInfoDO> selectPrizePageList(@Param("offset") Integer offset,
                                          @Param("pageSize") Integer pageSize);
}
