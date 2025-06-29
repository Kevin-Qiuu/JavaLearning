package com.kevinqiu.langchain4jdemo;

import com.kevinqiu.langchain4jdemo.bean.MyChatMessages;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档，mongodb 中每一条数据称之为是一个文档
     */
    @Test
    public void testInsert() {
        MyChatMessages myChatMessages = new MyChatMessages();
        myChatMessages.setContent("这是一条测试的聊天记录");
        mongoTemplate.insert(myChatMessages);
    }

    /**
     * 根据 _id 查询文档
     */
    @Test
    public void testFindById() {
        MyChatMessages message = mongoTemplate.findById("685f9bd9781373d5ade3efaf", MyChatMessages.class);
        System.out.println(message);
    }

    /**
     * 根据 _id 更新文档
     */
    @Test
    public void testUpdate() {
        // 构建查询条件
        Criteria criteria = Criteria.where("_id").is("685f9bd9781373d5ade3efaf");
        // 创建查询对象
        Query query = Query.query(criteria);
        // 创建更新对象
        Update update = new Update();
        update.set("content", "这是一条测试 mongodb 更新操作的聊天记录");

        // 发送请求，联合更新和查询
        mongoTemplate.upsert(query, update, MyChatMessages.class);

    }

    /**
     * 修改文档，如果文档不存在则新增
     */
    @Test
    public void testUpdate2() {
        // 构建查询条件
        Criteria criteria = Criteria.where("_id").is("100");
        // 构建查询对象
        Query query = Query.query(criteria);
        // 构建更新对象
        Update update = new Update();
        update.set("content", "测试新增或者修改文档");

        // 发送请求
        mongoTemplate.upsert(query, update, MyChatMessages.class);

    }

    /**
     * 删除文档
     */
    @Test
    public void testDelete() {
        Criteria criteria = Criteria.where("_id").is("100");
        Query query = Query.query(criteria);
        DeleteResult remove = mongoTemplate.remove(query, MyChatMessages.class);
        System.out.println(remove.getDeletedCount());
    }

}
