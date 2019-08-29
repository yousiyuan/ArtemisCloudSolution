package org.lnson.artemis.rabbit.service.impl;

import com.rabbitmq.client.Channel;
import org.lnson.artemis.common.ArtemisBeanDefinitionRegistryPostProcessor;
import org.lnson.artemis.rabbit.config.*;
import org.lnson.artemis.rabbit.delegate.MessageDelegate;
import org.lnson.artemis.rabbit.enums.ExchangeTypeEnum;
import org.lnson.artemis.rabbit.service.RabbitService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

@Service
public class RabbitServiceImpl implements RabbitService {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void initialize(ApplicationContext applicationContext) {
        this.beanDefinitionRegistry = applicationContext.getBean(ArtemisBeanDefinitionRegistryPostProcessor.class).getBeanDefinitionRegistry();
        RabbitConfigArgument argument = applicationContext.getBean(RabbitConfigArgument.class);

        declareExchangeDynamic(argument.getExchangeMap());
        declareQueueDynamic(argument.getQueueMap());
        declareBindingDynamic(argument.getBindingMap());

        // 动态注入SimpleMessageListenerContainer对象成功了，遇到的问题是：监听不起任何作用
        // simpleMessageListenerContainerFactory(applicationContext, argument.getQueueMap());

        RabbitAdmin rabbitAdmin = applicationContext.getBean(RabbitAdmin.class);
        rabbitAdmin.initialize();
    }

    /**
     * 动态声明 消息交换机 并 注入Spring IOC容器
     */
    private void declareExchangeDynamic(Map<String, RabbitExchange> rabbitExchangeMap) {
        Collection<RabbitExchange> rabbitExchangeCollection = rabbitExchangeMap.values();
        rabbitExchangeCollection.forEach(rabbitExchange -> {
            if (ExchangeTypeEnum.DIRECT_EXCHANGE.getExchangeType().equalsIgnoreCase(rabbitExchange.getExchangeType())) {
                beanDefinitionRegistry.registerBeanDefinition(
                        rabbitExchange.getExchangeName(),
                        BeanDefinitionBuilder.genericBeanDefinition(
                                DirectExchange.class,
                                () -> new DirectExchange(
                                        rabbitExchange.getExchangeName(),
                                        rabbitExchange.getExchangeDurable(),
                                        rabbitExchange.getExchangeAutoDelete(),
                                        rabbitExchange.getExchangeArguments()))
                                .getBeanDefinition());
            } else if (ExchangeTypeEnum.FANOUT_EXCHANGE.getExchangeType().equalsIgnoreCase(rabbitExchange.getExchangeType())) {
                beanDefinitionRegistry.registerBeanDefinition(
                        rabbitExchange.getExchangeName(),
                        BeanDefinitionBuilder.genericBeanDefinition(
                                FanoutExchange.class,
                                () -> new FanoutExchange(
                                        rabbitExchange.getExchangeName(),
                                        rabbitExchange.getExchangeDurable(),
                                        rabbitExchange.getExchangeAutoDelete(),
                                        rabbitExchange.getExchangeArguments()))
                                .getBeanDefinition());
            } else if (ExchangeTypeEnum.TOPIC_EXCHANGE.getExchangeType().equalsIgnoreCase(rabbitExchange.getExchangeType())) {
                beanDefinitionRegistry.registerBeanDefinition(
                        rabbitExchange.getExchangeName(),
                        BeanDefinitionBuilder.genericBeanDefinition(
                                TopicExchange.class,
                                () -> new TopicExchange(
                                        rabbitExchange.getExchangeName(),
                                        rabbitExchange.getExchangeDurable(),
                                        rabbitExchange.getExchangeAutoDelete(),
                                        rabbitExchange.getExchangeArguments()))
                                .getBeanDefinition());
            } else {
                throw new RuntimeException("an error exchange type value，declare exchange failure");
            }
        });
    }

    /**
     * 动态声明 消息队列 并 注入Spring IOC容器
     */
    private void declareQueueDynamic(Map<String, RabbitQueue> rabbitQueueMap) {
        Collection<RabbitQueue> rabbitQueueCollection = rabbitQueueMap.values();
        rabbitQueueCollection.forEach(rabbitQueue -> {
            beanDefinitionRegistry.registerBeanDefinition(
                    rabbitQueue.getQueueName(),
                    BeanDefinitionBuilder.genericBeanDefinition(
                            Queue.class,
                            () -> new Queue(
                                    rabbitQueue.getQueueName(),
                                    rabbitQueue.getQueueDurable(),
                                    rabbitQueue.getQueueExclusive(),
                                    rabbitQueue.getQueueAutoDelete(),
                                    rabbitQueue.getQueueArguments()))
                            .getBeanDefinition());
        });
    }

    /**
     * 动态声明 消息交换机与消息队列的绑定 并 注入Spring IOC容器
     */
    private void declareBindingDynamic(Map<String, RabbitBinding> rabbitBindingMap) {
        Collection<RabbitBinding> rabbitBindingCollection = rabbitBindingMap.values();
        rabbitBindingCollection.forEach(rabbitBinding -> {
            rabbitBinding.getRoutingKeyList().forEach((routingKey) -> {
                beanDefinitionRegistry.registerBeanDefinition(
                        MessageFormat.format("{0}_{1}_{2}", rabbitBinding.getExchange(), routingKey, rabbitBinding.getDestination()),
                        BeanDefinitionBuilder.genericBeanDefinition(
                                Binding.class,
                                () -> {
                                    return new Binding(
                                            rabbitBinding.getDestination(),
                                            rabbitBinding.getDestinationType(),
                                            rabbitBinding.getExchange(),
                                            routingKey,
                                            rabbitBinding.getBindArguments());
                                }
                        ).getBeanDefinition()
                );
            });
        });
    }

    /*
     **
     * 动态注入 消息监听器(消费者)
     */
    private void simpleMessageListenerContainerFactory(ApplicationContext applicationContext, Map<String, RabbitQueue> rabbitQueueMap) {
        for (RabbitQueue rabbitQueue : rabbitQueueMap.values()) {
            injectSimpleMessageListenerContainerDynamic(applicationContext, rabbitQueue);
        }
    }

    /**
     * 创建消息队列监听器(消费者)
     */
    private void injectSimpleMessageListenerContainerDynamic(ApplicationContext applicationContext, RabbitQueue rabbitQueue) {
        // ... ...
        ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);
        SimpleMessageListenerContainer simpleMessageListenerContainer = simpleMessageListenerContainer(connectionFactory, rabbitQueue);

        // ... ...
        beanDefinitionRegistry.registerBeanDefinition(
                MessageFormat.format("{0}_{1}", rabbitQueue.getQueueName(), System.currentTimeMillis()),
                BeanDefinitionBuilder.genericBeanDefinition(SimpleMessageListenerContainer.class, () -> simpleMessageListenerContainer).getBeanDefinition()
        );
    }

    /**
     * 创建监听器
     */
    private SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory, RabbitQueue rabbitQueue) {
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
        simpleMessageListenerContainer.setQueueNames(rabbitQueue.getQueueName());
        // 消费端的标签生成策略
        simpleMessageListenerContainer.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return MessageFormat.format("{0}_{1}", rabbitQueue.getQueueName(), String.valueOf(System.currentTimeMillis()));
            }
        });

        try {
            String delegateClass = rabbitQueue.getDelegateClass();
            String delegateMethod = rabbitQueue.getDelegateMethod();

            Class<? extends Object> clazz = Class.forName(delegateClass);
            Constructor<? extends Object> constructor = clazz.getDeclaredConstructor(new Class<?>[]{});
            Method method = clazz.getDeclaredMethod(delegateMethod, new Class<?>[]{Message.class, Channel.class});
            Object instance = constructor.newInstance(new Object[]{});

            // 定义 Java Bean
            // GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            // genericBeanDefinition.setBeanClass(clazz);
            // genericBeanDefinition.setSource(instance);
            // genericBeanDefinition.setScope(GenericBeanDefinition.SCOPE_SINGLETON);

            // 注入 Java Bean
            // beanDefinitionRegistry.registerBeanDefinition(MessageFormat.format("{0}_{1}", instance.getClass().getSimpleName(), String.valueOf(System.currentTimeMillis())), genericBeanDefinition);

            // 设置消息队列监听器
            simpleMessageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
                @Override
                public void onMessage(Message message, Channel channel) throws Exception {
                    method.invoke(instance, new Object[]{message, channel});
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return simpleMessageListenerContainer;
    }
}
