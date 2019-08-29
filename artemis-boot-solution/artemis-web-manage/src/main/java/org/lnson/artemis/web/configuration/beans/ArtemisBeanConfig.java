package org.lnson.artemis.web.configuration.beans;

import org.lnson.artemis.common.ArtemisBeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;

public class ArtemisBeanConfig {

    @Bean
    public ArtemisBeanDefinitionRegistryPostProcessor artemisBeanDefinitionRegistryPostProcessor() {
        return new ArtemisBeanDefinitionRegistryPostProcessor();
    }

}
