package org.lnson.artemis.launch.configuration;

import org.lnson.artemis.launch.listener.ArtemisServiceLaunchListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {ArtemisServiceLaunchListener.class, MybatisConf.class, RabbitConf.class})
@ComponentScan(basePackages = {"org.lnson.artemis.service"})
public class ArtemisServiceLaunchConfiguration {
}
