package org.artemis.config.server.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ArtemisConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtemisConfigClientApplication.class, args);
    }

}
