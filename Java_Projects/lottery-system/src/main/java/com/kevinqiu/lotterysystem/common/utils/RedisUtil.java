package com.kevinqiu.lotterysystem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    /**
     * RedisTemplate :  先将被存储的数据转换成 字节数组（不可读），再存储到redis中，读取的时候按照字节数组读取
     * StringRedisTemplate ： 直接存放的就是 string (可读)
     * 项目背景：String,String
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // ------------ String -------------

    /**
     * 设置键值对，但不指定存在时间
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean set(String key, String value) {
        try {
            stringRedisTemplate.opsForValue()
                .set(key, value);
            return true;
        } catch (Exception e){
            log.error("RedisUtil error, set(key: {}, value: {}), e: ", key, value, e);
            return false;
        }
    };

    /**
     * 设置键值对，指定键值对的存在时间
     * @param key
     * @param value
     * @param existTime
     * @return
     */
    public boolean set(String key, String value, Long existTime) {
        try {
            stringRedisTemplate.opsForValue()
                .set(key, value, existTime, TimeUnit.MINUTES);
            return true;
        } catch (Exception e){
            log.error("RedisUtil error, set(key: {}, value: {}, existTime: {}), e: ",
                    key, value, existTime, e);
            return false;
        }
    };

    /**
     * 获取指定健对应的值
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            String verificationCode = stringRedisTemplate.opsForValue().get(key);
            stringRedisTemplate.delete(key);
            return verificationCode;
        } catch (Exception e){
            log.error("RedisUtil error, get(key: {}), e: ",key, e);
            return null;
        }
    };

    /**
     * 删除指定的键值对
     * @param keys
     * @return
     */
    public boolean del(String... keys) {
        try {
            if (null != keys && keys.length > 0){
                if (keys.length == 1){
                    stringRedisTemplate.delete(keys[0]);
                } else {
                    stringRedisTemplate.delete(
                            (Collection<String>) CollectionUtils.arrayToList(keys));
                }
            }
            return true;
        } catch (Exception e){
            log.error("RedisUtil error, del(keys: {}), e: ", keys, e);
            return false;
        }
    };

    /**
     * 判断是否存在 key
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return StringUtils.hasText(key)
                    && Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
        } catch (Exception e){
            log.error("RedisUtil error, hasKey(key: {}), e: ", key, e);
            return false;
        }
    };

}
