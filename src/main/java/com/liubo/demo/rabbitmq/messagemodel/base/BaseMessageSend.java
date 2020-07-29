package com.liubo.demo.rabbitmq.messagemodel.base;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class BaseMessageSend {

    @Autowired
    ConnectionFactory connectionFactory;

    private final static String QUEUE_NAME = "base_message";

    public void send(String message) throws IOException, TimeoutException {
        // 从连接中创建通道，使用通道才能完成消息相关的操作
        Connection connection = connectionFactory.createConnection();

        Channel channel = connection.createChannel(false);
        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        // 消息内容
//        String message = "Hello World!";
        // 向指定的队列中发送消息
        if(StringUtils.isEmpty(message)){
            System.out.println("message is null");
            return ;
        }
        byte[] bytes = message.getBytes();

        String s = new String(bytes);
        if(StringUtils.isEmpty(s)){
            System.out.println("message is null");
            return ;
        }
        //MessageProperties
        AMQP.BasicProperties props = MessageProperties.TEXT_PLAIN;

        channel.basicPublish("", QUEUE_NAME, props, bytes);

        System.out.println(" [x] Sent '" + message + "'");

        //关闭通道和连接
        channel.close();
        connection.close();
    }

}
