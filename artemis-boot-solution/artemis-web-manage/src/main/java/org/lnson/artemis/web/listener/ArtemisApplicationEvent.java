package org.lnson.artemis.web.listener;

import org.lnson.artemis.rabbit.service.RabbitService;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * springboot 启动过程 事件监听处理
 */
public class ArtemisApplicationEvent implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationStartingEvent) {
            // TODO:
            // springboot应用启动且未作任何处理（除listener注册和初始化）的时候发送ApplicationStartingEvent
        } else if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            // TODO:
            // 确定springboot应用使用的Environment且context创建之前发送这个事件
        } else if (applicationEvent instanceof ApplicationPreparedEvent) {
            // TODO:
            // context已经创建且没有refresh发送个事件
        } else if (applicationEvent instanceof ApplicationStartedEvent) {
            // TODO:
            // context已经refresh且application and command-line runners（如果有） 调用之前发送这个事件
        } else if (applicationEvent instanceof ApplicationReadyEvent) {
            // TODO:
            // application and command-line runners （如果有）执行完后发送这个事件，此时应用已经启动完毕.这个事件比较常用，常常在系统启动完后做一些初始化操作
            ApplicationReadyEvent applicationReadyEvent = (ApplicationReadyEvent) applicationEvent;
            ApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();

            // RabbitMQ 声明 消息交换机 消息队列 绑定
            RabbitService rabbitService = applicationContext.getBean(RabbitService.class);
            rabbitService.initialize(applicationContext);
        } else if (applicationEvent instanceof ApplicationFailedEvent) {
            // TODO:
            // springboot应用启动失败后产生这个事件
        }
    }

}
