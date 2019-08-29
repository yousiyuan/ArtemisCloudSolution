package org.lnson.artemis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//启用Eureka客户端
@EnableEurekaClient
//启用Feign客户端
@EnableFeignClients
@SpringBootApplication
public class ArtemisConsumer80Application {

    public static void main(String[] args) {
        SpringApplication.run(ArtemisConsumer80Application.class, args);
    }

}
