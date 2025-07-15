package com.kevinqiu.lotterysystem;

import com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void restfulTest() {
        rabbitTemplate.convertAndSend(DirectRabbitConfig.EXCHANGE_NAME, "hello" , "restFulTest");
    }

    @Test
    public void publishTest() throws IOException, TimeoutException {
        // 建立 TCP 连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.137.156.234");
        factory.setPort(5672);
        factory.setVirtualHost("lottery-system");
        factory.setUsername("kevin");
        factory.setPassword("kevinqiu");
        Connection connection = factory.newConnection();

        // 创建信道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare("HelloWorld", true, false, false, null);

        // 通过简单模式发送消息
        String msg = "Hello,World!";
        channel.basicPublish("", "HelloWorld", null, msg.getBytes(StandardCharsets.UTF_8));
        System.out.println("消息发送成功！");

        // 释放资源
        channel.close();
        connection.close();
    }

    @Test
    public void consumeTest() throws IOException, TimeoutException {
        // 建立 TCP 连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.137.156.234");
        factory.setPort(5672);
        factory.setVirtualHost("lottery-system");
        factory.setUsername("kevin");
        factory.setPassword("kevinqiu");
        Connection connection = factory.newConnection();

        // 创建 channel
        Channel channel = connection.createChannel();

        // 声明使用的队列
        channel.queueDeclare("HelloWorld", true, false, false, null);

        // 消费资源
        channel.basicConsume("HelloWorld", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费了一个消息" + new String(body));
            }
        });

        // 中断连接
        channel.close();
        connection.close();

    }

}
