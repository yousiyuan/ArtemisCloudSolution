package org.lnson.artemis.regist;

import org.lnson.artemis.regist.listener.ArtemisServiceRegistListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaServer
@SpringBootApplication
public class ArtemisServiceRegistApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ArtemisServiceRegistApplication.class, args);
        ArtemisServiceRegistListener artemisWebListener = applicationContext.getBean(ArtemisServiceRegistListener.class);
        System.out.println("\r\n" + artemisWebListener.getRoot() + "\r\n");
    }

}
