package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.service.RabbitConsumerService;
import org.lnson.artemis.rabbit.service.RabbitProduceService;
import org.lnson.artemis.rabbit.service.channels.BankRabbitChannel;
import org.lnson.artemis.rabbit.service.channels.SampleRabbitChannel;
import org.lnson.artemis.rabbit.service.channels.constant.RabbitChannelConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service("bankRabbitService")
@EnableBinding(value = {BankRabbitChannel.class, Source.class, Sink.class, SampleRabbitChannel.class})
public class BankRabbitServiceImpl extends RabbitConsumerService implements RabbitProduceService {

    private MessageChannel output;

    @Autowired
    public BankRabbitServiceImpl(@Qualifier(RabbitChannelConstant.BANK_OUTPUT_CHANNEL) MessageChannel output) {
        this.output = output;
    }

    @Override
    public <T> void send(T msg) {
        output.send(MessageBuilder.withPayload(msg).build());
        System.out.println("生产者 bankRabbitService >> 发送消息：" + msg);
    }

    @Override
    @StreamListener(RabbitChannelConstant.BANK_INPUT_CHANNEL)
    // 收到消息后给予回复
    @SendTo(value = {RabbitChannelConstant.ACCOUNT_INPUT_CHANNEL})
    protected <T> Boolean receiveWithAck(Message<T> msg) {
        System.out.println("消费者 bankRabbitService >> 接收到消息：" + msg.getPayload());
        return true;
    }

    /**
     * 消息转发
     * 帮助 generateRabbitService 监听消息
     * 帮助 sampleRabbitService 发送消息
     *
     * 监听 输入通道 Sink.INPUT 接收到的消息
     * 使用 输出通道 RabbitChannelConstant.SAMPLE_OUTPUT_CHANNEL 发送消息，
     * 然后被 输入通道 RabbitChannelConstant.SAMPLE_INPUT_CHANNEL 监听到
     */
    @Override
    @ServiceActivator(inputChannel = Sink.INPUT, outputChannel = RabbitChannelConstant.SAMPLE_OUTPUT_CHANNEL)
    protected Message<String> transfer(Message<String> msg) {
        String payload = msg.getPayload();
        payload = MessageFormat.format("[{0} {1}]{2}", "bankRabbitService消息中转站", "转发消息", payload);
        return MessageBuilder.withPayload(payload).build();
    }

}
