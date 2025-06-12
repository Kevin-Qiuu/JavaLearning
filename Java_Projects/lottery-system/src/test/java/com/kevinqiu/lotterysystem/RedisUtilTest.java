package com.kevinqiu.lotterysystem;

import com.kevinqiu.lotterysystem.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    void test(){
        redisUtil.set("test", "1234");
        redisUtil.set("test1", "5678");
        redisUtil.set("test2", "9999");
        redisUtil.set("test3", "6666", 2L);
        System.out.println(redisUtil.hasKey("test"));
        System.out.println(redisUtil.get("test"));
        System.out.println(redisUtil.get("test2"));
        System.out.println(redisUtil.del("test"));
        System.out.println(redisUtil.hasKey("test"));
        System.out.println(redisUtil.get("test"));
        System.out.println(redisUtil.del("test1", "test2"));
        System.out.println(redisUtil.hasKey("test1"));
        System.out.println(redisUtil.hasKey("test2"));
    }

}
