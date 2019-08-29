package org.lnson.artemis.web.listener.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

// 在容器启动完成的时候进行执行
// 如果有多个runner需要指定一些顺序
// @Order(1)
public class ArtemisApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO:
        // 系统启动完成后可以做一些业务操作
    }

}
