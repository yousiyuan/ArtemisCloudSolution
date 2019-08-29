package org.lnson.artemis.web.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class RabbitAdminTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Before
    public void setUp() {
        // 调用rabbitAdmin的初始化方法,会自动创建配置文件中的所有已配置的 队列 交换机 和 绑定关系
        rabbitAdmin.initialize();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() {
        // 创建/声明消息队列 已存在队列不会重复创建
        rabbitAdmin.declareQueue(new Queue("artemis_test_queue_fanout", false));
        rabbitAdmin.declareQueue(new Queue("artemis_test_queue_topic", false));
        rabbitAdmin.declareQueue(new Queue("artemis_test_queue_direct", false));

        // 删除消息队列 不存在得消息队列不会报错
        rabbitAdmin.deleteQueue("artemis_test_queue_01");

        // 声明交换机 已存在交换机不会重复创建
        rabbitAdmin.declareExchange(new FanoutExchange("artemis_test_exchange_fanout", false, false));
        rabbitAdmin.declareExchange(new DirectExchange("artemis_test_exchange_topic", false, false));
        rabbitAdmin.declareExchange(new TopicExchange("artemis_test_exchange_direct", false, false));

        // 删除交换机 不存在也不会报错
        rabbitAdmin.deleteExchange("artemis_test_exchange_01");

        // 声明bingding 常规方式
        rabbitAdmin.declareBinding(new Binding(
                "artemis_test_queue_topic",     // 队列名称
                Binding.DestinationType.QUEUE,             // 绑定类型(队列)
                "artemis_test_exchange_topic",   // 交换机名称
                "orderService1.*",              // 路由key
                new HashMap<>()                            // 参数
        ));

        // 声明bingding 链式编程 1
        rabbitAdmin.declareBinding(BindingBuilder
                .bind(new Queue("artemis_test_queue_fanout", false))                            // 消息队列
                .to(new FanoutExchange("artemis_test_exchange_fanout", false, false)));  // 交换机

        // 声明bingding 链式编程 2
        rabbitAdmin.declareBinding(BindingBuilder
                .bind(new Queue("artemis_test_queue_direct", false))                               // 消息队列
                .to(new DirectExchange("artemis_test_exchange_direct", false, false))   // 交换机
                .with("orderService3"));
    }

}
