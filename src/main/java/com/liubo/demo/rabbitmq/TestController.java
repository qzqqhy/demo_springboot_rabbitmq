package com.liubo.demo.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liubo.demo.rabbitmq.messagemodel.base.BaseMessageSend;
import com.liubo.demo.rabbitmq.messagemodel.work.WorkMessageSend;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by hzlbo on 2016/7/2.
 */
@RestController
public class TestController {

    @Autowired
    RabbitTemplate rt;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String testSend() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("userId", "10086");
        map.put("userName", "qzqqhy");
        map.put("age", 20);
//        Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(map)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(map)).setMessageId("123").build();
        rt.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, message);
        return "ok";
    }



    @Autowired
    BaseMessageSend baseMessageSend;

    @RequestMapping("/baseMessageSend")
    @ResponseBody
    public String basemsgsender() throws IOException, TimeoutException {
        System.out.println("send string:hello world");
        baseMessageSend.send("hello world");
        return "sending...";
    }

    @Autowired
    WorkMessageSend workMessageSend;

    @RequestMapping("/workMessageSend")
    @ResponseBody
    public String baseMessageSd() throws Exception {
        System.out.println("send string work message :hello world");
        workMessageSend.send();
        return "sending...";
    }
}
