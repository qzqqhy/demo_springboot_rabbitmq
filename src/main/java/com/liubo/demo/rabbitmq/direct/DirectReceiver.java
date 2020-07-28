package com.liubo.demo.rabbitmq.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = "direct")
public class DirectReceiver {
    @RabbitHandler
    public void handler(String message) throws InterruptedException {
        //模拟耗时操作
        TimeUnit.SECONDS.sleep(10);
        System.out.println("接收消息："+message);
    }
}
