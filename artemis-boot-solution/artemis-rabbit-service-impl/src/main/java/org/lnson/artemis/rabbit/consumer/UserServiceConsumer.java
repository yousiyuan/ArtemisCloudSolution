package org.lnson.artemis.rabbit.consumer;

import com.rabbitmq.client.Channel;
import org.lnson.artemis.common.GenerateCommon;
import org.lnson.artemis.rabbit.utility.ReplyUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.nio.charset.StandardCharsets;

public class UserServiceConsumer {

    @RabbitHandler
    @RabbitListener(queues = {"#{rabbitConfigProperties.getProperty('rabbit.queue-map.user-service.queue-name')}"})
    public void messageHandler(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            System.out.println("UserService服务消费：" + msg);

            ReplyUtils.basicAck(deliveryTag, channel, 0);
        } catch (Exception ex) {
            System.out.println(GenerateCommon.printException(ex));

            ReplyUtils.basicNack(deliveryTag, channel, 0);
        }
    }

}
