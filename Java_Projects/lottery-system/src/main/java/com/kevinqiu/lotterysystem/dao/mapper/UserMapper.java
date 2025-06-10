package com.kevinqiu.lotterysystem.dao.mapper;

import com.kevinqiu.lotterysystem.dao.dataobject.Encrypt;
import com.kevinqiu.lotterysystem.dao.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select count(1) from user where email = #{email}")
    int countByMail(@Param("email") String mail);

    @Select("select  count(1) from user where phone_number = #{phoneNumber}")
    int countByPhone(@Param("phoneNumber") Encrypt phoneNumber);

    @Insert("insert into user (user_name, email, phone_number, password, identity)" +
            " values (#{userName}, #{mail}, #{phoneNumber}, #{password}, #{identity})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertRegisterUser(UserDO userDO);
}
