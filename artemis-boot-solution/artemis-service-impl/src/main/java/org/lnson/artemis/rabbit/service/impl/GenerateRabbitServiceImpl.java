package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.service.RabbitConsumerService;
import org.lnson.artemis.rabbit.service.RabbitProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service("generateRabbitService")
@EnableBinding(value = {Source.class})
public class GenerateRabbitServiceImpl extends RabbitConsumerService implements RabbitProduceService {

    private MessageChannel output;

    @Autowired
    public GenerateRabbitServiceImpl(@Qualifier(Source.OUTPUT) MessageChannel output) {
        this.output = output;
    }

    @Override
    public <T> void send(T msg) {
        output.send(MessageBuilder.withPayload(msg).build());
        System.out.println("生产者 generateRabbitService >> 发送消息：" + msg);
    }

}
