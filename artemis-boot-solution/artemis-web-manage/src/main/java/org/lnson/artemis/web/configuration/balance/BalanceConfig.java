package org.lnson.artemis.web.configuration.balance;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients(basePackages = {"org.lnson.artemis.rpc.service"})
public class BalanceConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule loadBalanceRule() {
        // 选择负载均衡策略
        IRule rule = null;

        // 1、轮询
        rule = new RoundRobinRule();

        // 2、随机
        // rule = new RandomRule();

        // 3、会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务, 还有并发的连接数超过阈值的服务,然后对剩余的服务列表按照轮询策略进行访问
        // rule = new AvailabilityFilteringRule();

        // 4、根据平均响应时间就算所有服务的权重,响应时间越快服务权重越大被选中 的概率越高.刚启动时如果统计信息不足,则使用RoudRobinRule策略, 等统计信息足够,会切换到WeightedResponseTimeRule
        // rule = new WeightedResponseTimeRule();

        // 5、看名字就知道这种负载均衡策略带有重试功能。首先RetryRule中又定义了一个subRule，它的实现类是RoundRobinRule，然后在RetryRule的choose(ILoadBalancer lb, Object key)方法中，每次还是采用RoundRobinRule中的choose规则来选择一个服务实例，如果选到的实例正常就返回，如果选择的服务实例为null或者已经失效，则在失效时间deadline之前不断的进行重试（重试时获取服务的策略还是RoundRobinRule中定义的策略），如果超过了deadline还是没取到则会返回一个null。
        // rule = new RetryRule();

        return rule;
    }

}
