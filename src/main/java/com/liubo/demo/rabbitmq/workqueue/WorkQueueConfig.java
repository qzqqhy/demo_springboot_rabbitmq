package com.liubo.demo.rabbitmq.workqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueConfig {
    @Bean
    public Queue workqueueQueue() {
        return new Queue("workqueue", false);//队列名字，是否持久化
    }

}
