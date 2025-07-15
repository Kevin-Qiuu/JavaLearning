package com.kevinqiu.lotterysystem.service.mq;

import com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig;
import com.kevinqiu.lotterysystem.common.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.kevinqiu.lotterysystem.common.configuration.DirectRabbitConfig.DLX_QUEUE_NAME;

// @RabbitHandler 通常都会与 @RabbitListener 搭配使用，会根据监听队列中的元素自动选择对应参数的 handler 方法

@Slf4j
@Component
@RabbitListener(queues = {DLX_QUEUE_NAME})
public class DlxMqReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RabbitHandler
    public void handlerEvents(Map<String, String> message) {

        log.info("处理死信队列的异常信息！message:{}", JacksonUtil.writeValueAsString(message));
        rabbitTemplate.convertAndSend(DirectRabbitConfig.EXCHANGE_NAME,
                DirectRabbitConfig.ROUTING, message);


        // 该流程是有问题的，在这里只是为了演示处理过程中发生异常：消息堆积-》处理异常-》消息重发
        // 正确的流程（扩展）：
        // 1、接收到异常消息，可以将异常消息存放到数据库表中
        // 2、存放后，当前异常消息消费完成，死信队列消息处理完成，但异常消息被我们持久化存储到表中了
        // 3、解决异常
        // 4、完成脚本任务，判断异常消息表中是否存在数据，如果存在，表示有消息未完成，此时处理消息
        // 5、处理消息：将消息发送给普通队列进行处理

        // 或者是采用定时器，到达定时后，删除记录，在定时器结束之前处理异常，然后在重新发送
    }

}
