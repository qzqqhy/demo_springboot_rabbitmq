package com.liubo.demo.rabbitmq.messagemodel.work;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkMessageSend {
    private final static String QUEUE_NAME = "test_work_queue";

    @Autowired
    ConnectionFactory connectionFactory;

    public void send() throws Exception {

        // 从连接中创建通道，使用通道才能完成消息相关的操作
        Connection connection = connectionFactory.createConnection();
        // 获取通道
        Channel channel = connection.createChannel(false);
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 循环发布任务
        for (int i = 0; i < 50; i++) {
            // 消息内容
            String message =new String("task .. " + i);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

//            Thread.sleep(i * 2);
        }
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
