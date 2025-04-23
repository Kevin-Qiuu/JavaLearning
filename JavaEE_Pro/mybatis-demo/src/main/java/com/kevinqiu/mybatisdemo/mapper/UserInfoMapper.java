package com.kevinqiu.mybatisdemo.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

// @Mapper
// 注解在程序运⾏时，mybatis 框架会⾃动⽣成接⼝的实现类对象(代理对象)，
// 并给交给 Spring 的 IOC 容器管理
@Mapper
public interface UserInfoMapper {

    @Select("select username, password, age, gender, phone from user_info")
    List<UserInfo> queryAll();

    // @Param 就是在做一个参数绑定操作，Mybatis 在不使用 @Param 时会直接使用变量名绑定形参，使用@Param 后则使用更换后的参数名来绑定形参
    // Mybatis 还会根据形参位置顺序额外添加一个名字绑定 param1,param2,……
    @Select("select username, password, age, gender, phone from user_info where id= #{id} ")
    List<UserInfo> queryById(@Param("id") Integer userId);

    // Mybatis 会把传递的对象以它的属性名称进行拆解作为参数传递给注释中的语句
    // @Options 注解可以返回添加行的主键
    // 当 useGeneratedKeys 设置为 true 时，会将添加行的主键设置在添加对象（UserInfo）的与注解 keyProperty 参数同名的属性中
    // 因此需要保证传递参数的对象中有一个与 keyProperty 参数同名的属性
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT into user_info (username, password, age, gender) " +
            "values (#{username}, #{password}, #{age}, #{gender}) ")
    Integer insertUser(UserInfo userInfo);

    @Update()



}
