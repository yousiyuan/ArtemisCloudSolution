package org.lnson.artemis.rabbit.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface CallbackService extends RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
}
