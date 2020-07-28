package com.liubo.demo.rabbitmq.messagemodel.work;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
public class WorkMessageRecv03 {


    @Autowired
    WorkMessageRecvFactory workMessageRecvFactory;

    @RabbitHandler
    @RabbitListener(queues = WorkQueueConfig.QUEUE_NAME)
    public void handler() throws IOException {
        Function<byte[], String> function = (a) -> {
            // body 即消息体
            String msg = new String(a);
            //模拟耗时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " 消费者 [3] received : " + msg + "!";
        };
        workMessageRecvFactory.handler(function);
    }
}
