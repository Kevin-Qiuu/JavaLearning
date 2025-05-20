package com.kevinqiu.springBookDemo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kevinqiu.springBookDemo.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserInfoWrapperTest {

    @Autowired
    private UserInfoPlusMapper userMapper;

    @Test
    void selectQuery(){
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<UserInfo>()
                .select("1")
                .eq("delete_flag", 0);
        System.out.println(userMapper.selectCount(userInfoQueryWrapper));
    }

    @Test
    void updateWrapper(){
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<UserInfo>()
                .select("id", "user_name", "password")
                .eq("delete_flag", 1);
        List<UserInfo> userInfos = userMapper.selectList(userInfoQueryWrapper);
        List<Integer> userIds = new ArrayList<Integer>();
        for (UserInfo userInfo : userInfos) {
            userIds.add(userInfo.getId());
        }
        UpdateWrapper<UserInfo> userInfoUpdateWrapper = new UpdateWrapper<UserInfo>()
                .set("delete_flag", 0)
                .in("id", userIds);
        userMapper.update(userInfoUpdateWrapper);

    }

    // lambda 查询器与非 lambda 查询器的好处就是，在编译阶段就可以发现 UserInfo 的字段是否发生了更改
    // 更容易找到 bug 的来源
    @Test
    void selectLambdaWrapper(){
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<UserInfo>()
                .select(UserInfo::getId, UserInfo::getUsername, UserInfo::getPassword, UserInfo::getCreateTime)
                .eq(UserInfo::getDeleteFlag, 0);
        userMapper.selectList(lambdaQueryWrapper).stream().forEach(System.out::println);
    }
}
