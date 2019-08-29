package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.service.RabbitConsumerService;
import org.lnson.artemis.rabbit.service.RabbitProduceService;
import org.lnson.artemis.rabbit.service.channels.AccountRabbitChannel;
import org.lnson.artemis.rabbit.service.channels.constant.RabbitChannelConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service("accountRabbitService")
@EnableBinding(value = {AccountRabbitChannel.class})
public class AccountRabbitServiceImpl extends RabbitConsumerService implements RabbitProduceService {

    private MessageChannel output;

    @Autowired
    public AccountRabbitServiceImpl(@Qualifier(RabbitChannelConstant.ACCOUNT_OUTPUT_CHANNEL) MessageChannel output) {
        this.output = output;
    }

    @Override
    public <T> void send(T msg) {
        output.send(MessageBuilder.withPayload(msg).build());
        System.out.println("生产者 accountRabbitService >> 发送消息：" + msg);
    }

    @Override
    @StreamListener(RabbitChannelConstant.ACCOUNT_INPUT_CHANNEL)
    protected <T> void receive(Message<T> msg) {
        T payload = msg.getPayload();
        if (payload instanceof Boolean) {
            System.out.println("收到消息应答：" + payload);
            return;
        }
        System.out.println("消费者 accountRabbitService >> 接收到消息：" + msg.getPayload());
    }

}
