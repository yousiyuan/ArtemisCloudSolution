package org.lnson.artemis.rabbit.service;

import org.springframework.messaging.Message;

public abstract class RabbitConsumerService {

    public <T> void send(T msg) {
    }

    protected <T> void receive(Message<T> msg) {
    }

    protected <T> Boolean receiveWithAck(Message<T> msg) {
        return true;
    }

    protected Message<String> transfer(Message<String> msg) {
        return msg;
    }

}
