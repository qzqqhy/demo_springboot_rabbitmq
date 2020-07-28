package com.liubo.demo.rabbitmq.direct;

import com.liubo.demo.rabbitmq.AmqpConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 消息发送 - 生产消息
 */
@Component
public class Sender {

    @Autowired
    AmqpTemplate rt;
    public void send(String message){
        System.out.println("发送消息："+message);
        rt.convertAndSend("direct",message);
    }
    
}
