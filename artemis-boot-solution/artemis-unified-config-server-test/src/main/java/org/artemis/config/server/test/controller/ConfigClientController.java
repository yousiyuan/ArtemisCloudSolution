package org.artemis.config.server.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.artemis.config.server.test.configuration.TestReadRemoteConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

//注意，需要更新配置的所有服务
@RefreshScope
@RestController
public class ConfigClientController {

    @Value("${author.name}")
    private String authorName;

    @Value("${author.gender}")
    private String authorGender;

    @Value("${author.age}")
    private Integer authorAge;

    /**
     * 测试 读取 从远程仓库获取的配置文件信息
     */
    @GetMapping("/")
    public String readConfig() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new HashMap<String, Object>() {{
            put("author.name", authorName);
            put("author.gender", authorGender);
            put("author.age", authorAge);
        }});
    }

}
