package org.artemis.dashboard.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableEurekaClient
@EnableHystrixDashboard
@SpringBootApplication
public class ArtemisDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtemisDashboardApplication.class, args);
    }

}
