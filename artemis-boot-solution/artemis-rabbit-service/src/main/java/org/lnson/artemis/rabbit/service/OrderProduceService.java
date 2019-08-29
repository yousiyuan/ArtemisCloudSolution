package org.lnson.artemis.rabbit.service;

public interface OrderProduceService {

    public abstract void sendQueueMessage(String message);

}
