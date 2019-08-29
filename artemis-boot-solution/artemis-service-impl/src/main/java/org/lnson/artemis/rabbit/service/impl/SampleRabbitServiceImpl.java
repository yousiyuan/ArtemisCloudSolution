package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.service.RabbitConsumerService;
import org.lnson.artemis.rabbit.service.RabbitProduceService;
import org.lnson.artemis.rabbit.service.channels.SampleRabbitChannel;
import org.lnson.artemis.rabbit.service.channels.constant.RabbitChannelConstant;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service("sampleRabbitService")
@EnableBinding(value = {SampleRabbitChannel.class})
public class SampleRabbitServiceImpl extends RabbitConsumerService implements RabbitProduceService {

    public SampleRabbitServiceImpl() {
    }

    @Override
    @StreamListener(RabbitChannelConstant.SAMPLE_INPUT_CHANNEL)
    protected <T> void receive(Message<T> msg) {
        System.out.println("消费者 sampleRabbitService >> 接收到消息：" + msg.getPayload());
    }

}
