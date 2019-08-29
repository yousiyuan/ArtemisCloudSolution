（Jar包）公共模块
artemis-common
说明：整个工程使用到的工具类都在这里

（Jar包）数据访问层 artemis-dao
说明：所有数据表实体类的普通CURD操作以及自定义SQL执行入口在这里

（Jar包）实体层 artemis-entity
说明：定义所有数据表的映射类以及业务相关model

（Jar包）服务接口层 artemis-service
说明：定义各种微服务接口，远程调用

（Jar包）服务实现层 artemis-service-impl
说明：基于mybatis通用mapper封装单数据表的CURD操作，支持自定义SQL的执行，若是涉及到多表操作仍需手写SQL，如联合查询

（Web工程）微服务层 artemis-service-launch-server
说明：1、集成Eureka Client实现服务注册；2、集成Hystrix实现服务降级与服务熔断；3、提供微服务监控支持的配置；4、简单集成SpringCloud Stream实现异步、流量削峰、应用解耦

（Web工程）微服务监控层 artemis-service-monitor-server
说明：实现Hystrix Dashboard实现微服务监控

（Web工程）artemis-service-regist-center
说明：集成Eureka Server提供微服务注册与发现的支持

（Web工程）统一配置中心 artemis-unified-config-server
说明：1、集成SpringCloud Config实现所有微服务到远程git仓库或码云仓库拉取配置信息；2、集成SpringCloud bus消息总线和WebHooks，实现远程仓库配置信息发生变化后所有微服务动态感知并在运行时刷新本地配置

（Web工程）统一配置中心测试 artemis-unified-config-server-test
说明：局限于PC资源有限，无法同时开启太多虚拟机运行微服务，此项目是用于测试证实集成了SpringCloud bus消息总线的微服务动态感知远程仓库的配置信息变化并刷新本地配置信息

（Web工程）zuul路由网关 artemis-unified-gateway-server
说明：集成Zuul路由网关，模拟签名验证，服务重定向，限流

（jar包）artemis-rabbit-service
（jar包）artemis-rabbit-service-impl
说明：SpringBoot工程启动完成后，动态注入所有的交换机、消息队列、交换机和消息队列绑定 的java bean，初始化RabbitMQ等。【遗憾：动态的注入监听器的java bean，监听器收不到队列消息，后续翻源码再排查】

（Web工程）后台管理 artemis-web-manage
说明：1、远程调用微服务；2、实现软负载均衡；3、测试调用前面整合的所有工程
注意，首次运行Web项目，请把后台的监听器先注释掉，因为交换机、消息队列和绑定都是项目启动后再动态注入声明的；之后运行没有丝毫影响


叹：：：
局限于个人时间、精力、水平以及语言障碍等诸多因素，很多地方整合的不尽人意，说得也不够详细，看就完了！实际工作中，根据需求请进行更加详细合理的配置。
