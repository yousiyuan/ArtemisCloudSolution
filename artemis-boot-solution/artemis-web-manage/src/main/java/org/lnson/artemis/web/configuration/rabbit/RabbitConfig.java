package org.lnson.artemis.web.configuration.rabbit;

import com.rabbitmq.client.Channel;
import org.lnson.artemis.rabbit.config.RabbitConfigArgument;
import org.lnson.artemis.rabbit.consumer.GoodsServiceConsumer;
import org.lnson.artemis.rabbit.consumer.UserServiceConsumer;
import org.lnson.artemis.rabbit.service.CallbackService;
import org.lnson.artemis.yaml.DefaultYamlSourceFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;

//启用注解方式的RabbitMQ消费者
@EnableRabbit
@Import(value = {UserServiceConsumer.class, GoodsServiceConsumer.class})
//////////////////////////////////////////////////////////////////////////////////////////////////
@ComponentScan(basePackages = {"org.lnson.artemis.rabbit.service"})
@PropertySource(value = {"classpath:/rabbit.cfg.yaml"}, factory = DefaultYamlSourceFactory.class)
public class RabbitConfig {

    @Autowired
    private RabbitConfigArgument rabbitConfigArgument;

    @Autowired
    private CallbackService callbackService;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitConfigArgument.getAddresses());
        connectionFactory.setVirtualHost(rabbitConfigArgument.getVirtualHost());
        connectionFactory.setUsername(rabbitConfigArgument.getUserName());
        connectionFactory.setPassword(rabbitConfigArgument.getPassword());
        // 启用回调确认实现消息可靠性
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，多次设置回调类会报错
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 使用单独的发送连接，避免生产者由于各种原因阻塞而导致消费者同样阻塞
        rabbitTemplate.setUsePublisherConnection(true);
        /*
         * 当mandatory标志位设置为true时
         * 如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息
         * 那么broker会调用basic.return方法将消息返还给生产者
         * 当mandatory设置为false时，出现上述情况broker会直接将消息丢弃
         */
        rabbitTemplate.setMandatory(true);
        // ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调
        rabbitTemplate.setConfirmCallback(callbackService);
        // ReturnCallback接口用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调
        rabbitTemplate.setReturnCallback(callbackService);

        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setPrefetchCount(1);
        simpleRabbitListenerContainerFactory.setDefaultRequeueRejected(false);
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleRabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        simpleRabbitListenerContainerFactory.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return MessageFormat.format("{0}_{1}", "consumer", String.valueOf(System.currentTimeMillis()));
            }
        });

        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        return simpleRabbitListenerContainerFactory;
    }

    @Bean("artemis_order_service_queue_listener")
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        // 设置 监听器和信道 暴露出来
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        // 设置多个并发消费者一起消费，并支持运行时动态修改
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        // 设置最多的并发消费者
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        // 设置消费者的Arguments
        simpleMessageListenerContainer.setConsumerArguments(new HashMap<String, Object>());
        // 设置是否重回队列，false表示不允许（当消费失败时）
        simpleMessageListenerContainer.setDefaultRequeueRejected(false);
        // 设置手动签收
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // Set basicQos
        simpleMessageListenerContainer.setPrefetchCount(1);
        // 设置被监听的消息队列
        simpleMessageListenerContainer.setQueueNames("artemis_order_service_queue");
        // 消费端的标签生成策略
        simpleMessageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return MessageFormat.format("{0}_{1}", "artemis_order_service_queue", String.valueOf(System.currentTimeMillis()));
            }
        });
        // 设置消息队列监听器
        simpleMessageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String delegateClass = "org.lnson.artemis.rabbit.delegate.MessageDelegate";
                String delegateMethod = "onMessage";

                Class<?> clazz = Class.forName(delegateClass);
                Constructor<?> constructor = clazz.getDeclaredConstructor(new Class<?>[]{});
                Method method = clazz.getDeclaredMethod(delegateMethod, new Class<?>[]{Message.class, Channel.class});

                Object instance = constructor.newInstance(new Object[]{});
                method.invoke(instance, message, channel);
            }
        });
        return simpleMessageListenerContainer;
    }

}
