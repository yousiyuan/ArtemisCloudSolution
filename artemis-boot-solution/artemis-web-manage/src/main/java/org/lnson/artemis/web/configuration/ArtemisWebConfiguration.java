package org.lnson.artemis.web.configuration;

import org.lnson.artemis.rabbit.config.RabbitConfigArgument;
import org.lnson.artemis.web.configuration.balance.BalanceConfig;
import org.lnson.artemis.web.configuration.beans.ArtemisBeanConfig;
import org.lnson.artemis.web.configuration.rabbit.RabbitConfig;
import org.lnson.artemis.web.listener.ArtemisApplicationEvent;
import org.lnson.artemis.web.listener.ArtemisWebServerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.util.*;

@Configuration
@Import(value = {ArtemisApplicationEvent.class, ArtemisWebServerListener.class, ArtemisBeanConfig.class,
        BalanceConfig.class, RabbitConfig.class})
public class ArtemisWebConfiguration {

    @Autowired
    private Environment environment;

    /**
     * 通过 松散绑定 使用 JavaBean 读取 RabbitMQ配置信息
     */
    @Bean
    public RabbitConfigArgument rabbitConfigArgument() {
        return Binder.get(environment).bind("rabbit", Bindable.of(RabbitConfigArgument.class)).get();
    }

    /**
     * 使用Properties 读取 RabbitMQ配置信息
     */
    @Bean("rabbitConfigProperties")
    public Properties rabbitConfigProperties() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("rabbit.cfg.yaml"));
        return yaml.getObject();
    }

    /**
     * 使用Map 读取 RabbitMQ配置信息
     */
    @Bean("rabbitConfigMap")
    public Map<String, Object> rabbitConfigMap() {
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("rabbit.cfg.yaml"));
        return yamlMapFactoryBean.getObject();
    }

}
