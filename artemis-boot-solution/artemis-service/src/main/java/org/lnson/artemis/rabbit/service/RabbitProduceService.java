package org.lnson.artemis.rabbit.service;

public interface RabbitProduceService {

    <T> void send(T msg);

}
