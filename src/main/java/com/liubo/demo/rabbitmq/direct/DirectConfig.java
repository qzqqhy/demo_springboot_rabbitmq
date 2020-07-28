package com.liubo.demo.rabbitmq.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.core.*;

@Configuration
public class DirectConfig {

    @Bean
    public Queue directQueue() {
        return new Queue("direct", false);//队列名字，是否持久化
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct",false,false);
    }
    @Bean
    public Binding binding(Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("direct");
    }
}
