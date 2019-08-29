package org.lnson.artemis.rabbit.utility;

import com.rabbitmq.client.Channel;

public class ReplyUtils {

    public static void basicAck(Long deliveryTag, Channel channel, Integer times) {
        if (times >= 3)
            return;
        try {
            Thread.sleep(1000 * times);
            // 处理完毕之后发送签收回执，multiple设置false表示不批量签收
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            basicAck(deliveryTag, channel, ++times);
        }
    }

    public static void basicNack(Long deliveryTag, Channel channel, Integer times) {
        if (times >= 3)
            return;
        try {
            Thread.sleep(1000 * times);
            // 拒绝消息
            // 第三个参数说明：true - 表示重回队列，false - 表示进入死信队列
            channel.basicNack(deliveryTag, false, false);
        } catch (Exception e) {
            basicNack(deliveryTag, channel, ++times);
        }
    }

}
