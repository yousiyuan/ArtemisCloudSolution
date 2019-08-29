package org.lnson.artemis.rabbit.service;

public interface UserProduceService {

    public abstract void sendQueueMessage(String message);

}
