package org.artemis.gateway.server.configuration;

import org.artemis.gateway.server.filters.ArtemisErrorZuulFilter;
import org.artemis.gateway.server.filters.ArtemisRateLimitZuulFilter;
import org.artemis.gateway.server.filters.ArtemisResponseZuulFilter;
import org.artemis.gateway.server.filters.ArtemisTokenZuulFilter;
import org.artemis.gateway.server.degradation.ArtemisZuulFallbackHandler;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisGatewayConfiguration {

    /**
     * 前置过滤实现token验证
     */
    @Bean
    public ArtemisTokenZuulFilter artemisTokenZuulFilter() {
        return new ArtemisTokenZuulFilter();
    }

    /**
     * 后置过滤向浏览器回写cookie
     */
    @Bean
    public ArtemisResponseZuulFilter artemisResponseZuulFilter() {
        return new ArtemisResponseZuulFilter();
    }

    /**
     * 限流
     */
    @Bean
    public ArtemisRateLimitZuulFilter artemisRateLimitZuulFilter() {
        return new ArtemisRateLimitZuulFilter();
    }

    /**
     * 异常处理
     */
    @Bean
    public ArtemisErrorZuulFilter artemisErrorZuulFilter() {
        return new ArtemisErrorZuulFilter();
    }

    /**
     * 自定义路由规则
     * 假如有这样命名的holiday-v1、holiday-v2服务名称，则相对应的路由访问路径变为/v1/holiday，/v2/holiday
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}");
    }

    /**
     * 在zuul上实现服务降级和服务熔断
     */
    @Bean
    public ArtemisZuulFallbackHandler artemisZuulFallbackHandler() {
        return new ArtemisZuulFallbackHandler();
    }

}
