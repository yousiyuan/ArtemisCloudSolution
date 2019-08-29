package org.lnson.artemis.rabbit.service;

public interface GoodsProduceService {

    public abstract void sendQueueMessage(String message);

}
