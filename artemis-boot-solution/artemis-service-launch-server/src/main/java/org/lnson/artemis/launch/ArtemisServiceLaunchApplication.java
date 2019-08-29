package org.lnson.artemis.launch;

import org.lnson.artemis.launch.listener.ArtemisServiceLaunchListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

// 启用 Hystrix “断路器”
@EnableCircuitBreaker
// 启用Eureka客户端
@EnableEurekaClient
@SpringBootApplication
public class ArtemisServiceLaunchApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ArtemisServiceLaunchApplication.class, args);
        ArtemisServiceLaunchListener artemisWebListener = applicationContext.getBean(ArtemisServiceLaunchListener.class);
        System.out.println("\r\n" + artemisWebListener.getRoot() + "\r\n");
    }

}
