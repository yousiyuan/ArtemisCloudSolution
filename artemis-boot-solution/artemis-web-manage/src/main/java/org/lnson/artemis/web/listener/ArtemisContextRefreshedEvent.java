package org.lnson.artemis.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class ArtemisContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {

    private volatile AtomicBoolean hasInit = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // TODO:
        // ContextRefreshedEvent 事件会在Spring容器初始化完成后触发该事件
        // 由于web应用会出现父子容器，这样就会重复触发两次或更多次
        // 防止重复触发
        if (!hasInit.compareAndSet(false, true))
            return;

        // TODO:
        // do something for application initialize
    }

}
