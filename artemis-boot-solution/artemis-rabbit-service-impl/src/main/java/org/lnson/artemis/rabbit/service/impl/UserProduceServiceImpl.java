package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.service.UserProduceService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class UserProduceServiceImpl implements UserProduceService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("#{rabbitConfigProperties.getProperty('rabbit.exchange-map.user-service.exchange-name')}")
    private String exchangeName;

    @Value("#{rabbitConfigProperties.getProperty('rabbit.queue-map.user-service.queue-name')}")
    private String queueName;

    @Override
    public void sendQueueMessage(String message) {
        String uid = UUID.randomUUID().toString();
        System.out.println("UserProduceService：准备发送消息 ... ...(" + uid + ")");

        CorrelationData correlationData = new CorrelationData(uid);
        correlationData.setReturnedMessage(new Message(correlationData.getId().getBytes(StandardCharsets.UTF_8), new MessageProperties()));

        System.out.println("这里不使用交换机：" + exchangeName);

        // 设置消息的属性
        MessageProperties msgProp = new MessageProperties();
        msgProp.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);//设置请求头
        msgProp.setExpiration("10000");//设置消息超时
        msgProp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);//消息持久化

        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8), msgProp);

        // 直接发送消息到消息队列中
        rabbitTemplate.convertAndSend(queueName, msg, correlationData);
    }

}
