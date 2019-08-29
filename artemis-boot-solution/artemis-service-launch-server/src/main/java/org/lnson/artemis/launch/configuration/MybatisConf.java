package org.lnson.artemis.launch.configuration;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

@MapperScan(basePackages = {"org.lnson.artemis.dao"})
public class MybatisConf {

    @Bean
    public ConfigurationCustomizer getCustomizer() {
        return (Configuration configuration) -> {
            // 设置 数据库字段（下划线和大小驼峰命名模式）与实体类字段（小驼峰命名模式）的映射
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }

}
