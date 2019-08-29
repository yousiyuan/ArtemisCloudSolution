package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.enums.RoutingKeyEnum;
import org.lnson.artemis.rabbit.service.GoodsProduceService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class GoodsProduceServiceImpl implements GoodsProduceService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("#{rabbitConfigProperties.getProperty('rabbit.exchange-map.goods-service.exchange-name')}")
    private String exchangeName;

    @Override
    public void sendQueueMessage(String message) {
        String uid = UUID.randomUUID().toString();
        System.out.println("GoodsProduceService：准备发送消息 ... ...(" + uid + ")");

        CorrelationData correlationData = new CorrelationData(uid);
        correlationData.setReturnedMessage(new Message(correlationData.getId().getBytes(StandardCharsets.UTF_8), new MessageProperties()));

        MessageProperties msgProp = new MessageProperties();
        msgProp.setContentType(MessageProperties.CONTENT_TYPE_JSON);//设置请求头
        msgProp.setExpiration("10000");
        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8), msgProp);
        rabbitTemplate.send(exchangeName, RoutingKeyEnum.LIST_KEY.getValue(), msg, correlationData);
    }

}
