package org.lnson.artemis.rabbit.service.impl;

import org.lnson.artemis.rabbit.service.CallbackService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class CallbackServiceImpl implements CallbackService {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("===== (correlationData, ack, cause)->{...} 开始 =====");
        System.out.println("correlationData：" + correlationData.getId());
        System.out.println("ack：" + ack);
        System.out.println("cause：" + cause);
        System.out.println("===== (correlationData, ack, cause)->{...} 结束 =====");
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("===== (message, replyCode, replyText, exchange, routingKey)->{...} 开始 =====");
        System.out.println("message：" + new String(message.getBody(), StandardCharsets.UTF_8));
        System.out.println("replyCode：" + replyCode);
        System.out.println("replyText：" + replyText);
        System.out.println("exchange：" + exchange);
        System.out.println("routingKey：" + routingKey);
        System.out.println("===== (message, replyCode, replyText, exchange, routingKey)->{...} 结束 =====");
    }

}
