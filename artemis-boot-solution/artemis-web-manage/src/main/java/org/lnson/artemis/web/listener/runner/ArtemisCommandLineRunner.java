package org.lnson.artemis.web.listener.runner;

import org.springframework.boot.CommandLineRunner;

// 在容器启动完成的时候进行执行
// 如果有多个runner需要指定一些顺序
// @Order(1)
public class ArtemisCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // TODO:
        // 我们会有在项目服务启动的时候就去加载一些数据或做一些事情这样的需求，为了解决这样的问题，在Spring Boot 中，实现接口 CommandLineRunner 即可
    }

}
