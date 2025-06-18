package com.kevinqiu.lotterysystem;

import com.kevinqiu.lotterysystem.common.pojo.CommonResult;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import com.kevinqiu.lotterysystem.controller.UserController;
import com.kevinqiu.lotterysystem.controller.result.UserBaseInfoResult;
import com.kevinqiu.lotterysystem.dao.dataobject.Encrypt;
import com.kevinqiu.lotterysystem.dao.dataobject.UserDO;
import com.kevinqiu.lotterysystem.dao.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SqlTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserController userController;

    @Test
    void countByMailTest(){
        System.out.println(userMapper.countByMail("123456@qq.com"));
    }

    @Test
    void countByPhoneTest(){
        System.out.println(userMapper.countByPhone(new Encrypt("13344447777")));
    }

    @Test
    void selectUserByEmail(){
        UserDO userDO = userMapper.selectByMail("1477527150@qq.com");
        System.out.println(JacksonUtil.writeValueAsString(userDO));
    }

    @Test
    void findUserBaseInfoTest(){
        CommonResult<List<UserBaseInfoResult>> userBaseInfo = userController.findUserBaseInfo(null);
        System.out.println(JacksonUtil.writeValueAsString(userBaseInfo));
    }
}
