package org.lnson.artemis.web;

import org.lnson.artemis.web.listener.ArtemisWebServerListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ArtemisWebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ArtemisWebApplication.class);
        ArtemisWebServerListener artemisWebServerListener = applicationContext.getBean(ArtemisWebServerListener.class);
        System.out.println("\r\n" + artemisWebServerListener.getRoot() + "\r\n");
    }

}
