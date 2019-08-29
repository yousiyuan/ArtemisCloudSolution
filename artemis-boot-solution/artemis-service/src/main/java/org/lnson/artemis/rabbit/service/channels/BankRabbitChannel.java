package org.lnson.artemis.rabbit.service.channels;

import org.lnson.artemis.rabbit.service.channels.constant.RabbitChannelConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface BankRabbitChannel {

    @Output(RabbitChannelConstant.BANK_OUTPUT_CHANNEL)
    MessageChannel outputChannel();

    @Input(RabbitChannelConstant.BANK_INPUT_CHANNEL)
    SubscribableChannel inputChannel();

}
