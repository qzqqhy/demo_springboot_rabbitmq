package com.liubo.demo.rabbitmq.messagemodel.work;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueConfig {
    public final static String QUEUE_NAME = "test_work_queue";
    @Bean(name = "workqueueQueue")
    public Queue workqueueQueue() {
        return new Queue(QUEUE_NAME, false);//队列名字，是否持久化
    }

    @Bean
    public Connection connection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");

        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }

}
