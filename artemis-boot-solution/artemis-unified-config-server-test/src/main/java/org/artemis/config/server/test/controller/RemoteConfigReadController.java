package org.artemis.config.server.test.controller;

import org.artemis.config.server.test.configuration.TestReadRemoteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteConfigReadController {

    private TestReadRemoteConfig testReadRemoteConfig;

    @Autowired
    public RemoteConfigReadController(TestReadRemoteConfig testReadRemoteConfig) {
        this.testReadRemoteConfig = testReadRemoteConfig;
    }

    /**
     * 测试 读取 从远程仓库获取的配置文件信息
     */
    @GetMapping("/read")
    public String readConfig222() {
        return testReadRemoteConfig.toString();
    }

}
