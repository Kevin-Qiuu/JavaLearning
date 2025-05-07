package com.kevinqiu.mybatisdemo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 通过 XML 的方式来使用 MyBatis，进而实现复杂的 SQL 功能
// XML 实现方式相比注解，结构更清晰，代码实现的自由度更高
// 注解也是基于 XML 做的进一步的封装
@Mapper
public interface UserInfoXMLMapper {

    List<UserInfo> selectAllUser();

    UserInfo selectUserById(Integer id);

    List<UserInfo> selectUserByUsername(String username);

    List<UserInfo> selectUserByLikeUsername(String username);

    List<UserInfo> selectUserByCondition(UserInfo userInfo);

    Integer insertUser(UserInfo userInfo);

    Integer deleteUserById(Integer id);

    Integer deleteUsersByIds(List<Integer> ids);

    Integer updateUserById(UserInfo userInfo);

    Integer updateUserByCondition(UserInfo userInfo);

    Integer insertUserByCondition(UserInfo userInfo);


}
