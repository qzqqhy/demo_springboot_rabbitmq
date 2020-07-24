package com.liubo.demo.rabbitmq;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liubo.demo.rabbitmq.person.dao.PersonDao;
import com.liubo.demo.rabbitmq.person.model.PersonDO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 监听的业务类，实现接口MessageListener
 * Created by hzlbo on 2016/7/1.
 */

@Component("simpleListener")
public class Receiver implements ChannelAwareMessageListener {
    public Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private PersonDao personDao;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("##### = {}", new String(message.getBody()));

        long tag=message.getMessageProperties().getDeliveryTag();
        try {


            byte[] msg = message.getBody();
            Map<String, Object> param = objectMapper.readValue(msg,Map.class);
            String id = (String) param.get("id");
            String userId = (String) param.get("userId");
            String userName = (String) param.get("userName");
            int age = (int) param.get("age");

            PersonDO personDO = new PersonDO(id,userId,userName,age);
            //模拟耗时操作
            TimeUnit.SECONDS.sleep(10);
            boolean result = personDao.addPerson(personDO) > 0;
            if (!result) {
                logger.error("消息消费失败");
            } else {
                logger.info("消息消费成功");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//手动消息应答
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);//true 重新放入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);//对于处理不了的异常消息
            byte[] msg = message.getBody();
            Map<String, Object> param = objectMapper.readValue(msg,Map.class);
            String id = (String) param.get("id");
            String userId = (String) param.get("userId");
            String userName = (String) param.get("userName");
            int age = (int) param.get("age");

            PersonDO personDO = new PersonDO(id,userId,userName,age);
            //发送到失败队列
            template.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY_FAIL, personDO);
        }
    }


}
