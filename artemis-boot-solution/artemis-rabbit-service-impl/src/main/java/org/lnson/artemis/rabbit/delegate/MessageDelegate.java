package org.lnson.artemis.rabbit.delegate;

import com.rabbitmq.client.Channel;
import org.lnson.artemis.common.GenerateCommon;
import org.lnson.artemis.rabbit.utility.ReplyUtils;
import org.springframework.amqp.core.Message;

import java.nio.charset.StandardCharsets;

public class MessageDelegate {

    public void onMessage(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("OrderService服务消费：" + msg);

            ReplyUtils.basicAck(deliveryTag, channel, 0);
        } catch (Exception ex) {
            System.out.println(GenerateCommon.printException(ex));

            ReplyUtils.basicNack(deliveryTag, channel, 0);
        }
    }

}
