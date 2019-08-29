package org.lnson.artemis.web.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lnson.artemis.common.JsonCommon;
import org.lnson.artemis.rabbit.config.RabbitConfigArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Properties;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class ArtemisWebApplicationTest {

    @Autowired
    private RabbitConfigArgument rabbitConfigArgument;

    @Autowired
    @Qualifier("rabbitConfigProperties")
    private Properties rabbitConfigProperties;

    @Autowired
    @Qualifier("rabbitConfigMap")
    private Map<String, Object> rabbitConfigMap;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void rabbitConfigReadTest1() {
        System.out.println(JsonCommon.serializeObject(rabbitConfigArgument));
    }

    @Test
    public void rabbitConfigReadTest2() {
        System.out.println(JsonCommon.serializeProperties(rabbitConfigProperties));
    }

    @Test
    public void rabbitConfigReadTest3() {
        System.out.println(JsonCommon.serializeObject(rabbitConfigMap));
    }

}
