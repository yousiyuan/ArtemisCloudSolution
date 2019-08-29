package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.enums.RoutingKeyEnum;
import org.lnson.artemis.rabbit.service.OrderProduceService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderProduceServiceImpl implements OrderProduceService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("#{rabbitConfigProperties.getProperty('rabbit.exchange-map.order-service.exchange-name')}")
    private String exchangeName;

    @Override
    public void sendQueueMessage(String message) {
        String uid = UUID.randomUUID().toString();
        System.out.println("OrderProduceService：准备发送消息 ... ...(" + uid + ")");

        CorrelationData correlationData = new CorrelationData(uid);
        correlationData.setReturnedMessage(new Message(correlationData.getId().getBytes(StandardCharsets.UTF_8), new MessageProperties()));

        System.out.println("接收消息的交换机：" + exchangeName);

        // 设置消息的属性
        MessageProperties msgProp = new MessageProperties();
        msgProp.setContentType(MessageProperties.CONTENT_TYPE_JSON);//设置请求头
        msgProp.setExpiration("10000");//设置消息超时
        msgProp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);//消息持久化

        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8), msgProp);

        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("date", new Date());
        rabbitTemplate.convertAndSend(exchangeName, RoutingKeyEnum.LIST_KEY.getValue(), map, correlationData);
    }

}
