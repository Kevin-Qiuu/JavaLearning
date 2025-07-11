package com.kevinqiu.lotterysystem.dao.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 这个 Handler，是用来做数据库非主键参数回填的，但是需要插入的对象继承 BaseMapper
 * 同时需要使用 BaseMapper 接口的方法，所以必须使用 MyBatis Plus 才可以
 */

@Component
public class BaseDOMetaObjectHandler implements MetaObjectHandler {

    /**
     * metaObject 表示当前正在插入或更新的实体对象；
     * 填充完成后，这个值会被 MyBatis Plus 插入到数据库中。
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("gmtCreate", new Date(), metaObject);
        setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
