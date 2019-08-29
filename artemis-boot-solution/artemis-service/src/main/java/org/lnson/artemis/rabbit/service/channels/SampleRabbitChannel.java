package org.lnson.artemis.rabbit.service.channels;

import org.lnson.artemis.rabbit.service.channels.constant.RabbitChannelConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SampleRabbitChannel {

    @Output(RabbitChannelConstant.SAMPLE_OUTPUT_CHANNEL)
    MessageChannel outputChannel();

    @Input(RabbitChannelConstant.SAMPLE_INPUT_CHANNEL)
    SubscribableChannel inputChannel();

}
