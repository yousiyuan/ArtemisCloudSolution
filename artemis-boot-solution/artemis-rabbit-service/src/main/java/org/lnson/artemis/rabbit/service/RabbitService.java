package org.lnson.artemis.rabbit.service;

import org.springframework.context.ApplicationContext;

public interface RabbitService {

    public abstract void initialize(ApplicationContext applicationContext);

}
