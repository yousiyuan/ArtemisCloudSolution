package org.lnson.artemis.launch.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lnson.artemis.common.JsonCommon;
import org.lnson.artemis.entity.User;
import org.lnson.artemis.service.UserMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class MyBatisTest {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Autowired
    private UserMapperService userService;

    @Before
    public void setUp() {
        logger.debug("================= 测试开始 =================");
    }

    @After
    public void tearDown() {
        logger.debug("================= 测试结束 =================");
    }

    @Test
    public void selectTest() {
        System.out.println(JsonCommon.serializeObject(userService.selectForList()));
        System.out.println("\r\n");

        System.out.println(JsonCommon.serializeObject(userService.selectForList(new User() {{
            setStatus(0);
        }})));
        System.out.println("\r\n");

        System.out.println(JsonCommon.serializeObject(userService.selectForObject(12)));
        System.out.println("\r\n");

        System.out.println(JsonCommon.serializeObject(userService.selectForObject(new User() {{
            setUserCode("33bb89bc-5cb8-44c5-ac9f-cd1d67f0cde2");
        }})));
        System.out.println("\r\n");

        System.out.println(userService.selectCount(null));
        System.out.println("\r\n");
    }

    @Test
    public void insertTest() {
        User record = new User();
        record.setUserCode(UUID.randomUUID().toString());
        record.setUserName("流年公子");
        record.setPassword("my artemis 123");
        record.setGender(0);
        record.setAddress("古希腊");
        record.setAge(99999);
        record.setBirthday(null);
        record.setStatus(1);
        record.setCreateDate(null);
        record.setUpdateDate(new Date());
        userService.insert(record);
        System.out.println("insert插入数据：" + record.getUserId());

        User user = new User();
        user.setUserCode(UUID.randomUUID().toString());
        user.setUserName("觅尽尘缘");
        user.setPassword("liu.nian.son 123");
        user.setGender(0);
        user.setAddress("古希腊");
        user.setAge(99999);
        user.setBirthday(null);
        user.setStatus(1);
        user.setCreateDate(null);
        user.setUpdateDate(new Date());
        userService.insertSelective(user);
        System.out.println("insertSelective插入数据：" + user.getUserId());
    }

    @Test
    public void updateTest() {
        User record = new User();
        record.setUserId(318);
        record.setUserCode(UUID.randomUUID().toString());
        record.setUserName("流年公子-1989-01-01");
        record.setPassword("my artemis 123");
        record.setGender(0);
        record.setAddress("古希腊");
        record.setAge(99999);
        record.setBirthday(null);
        record.setStatus(1);
        record.setCreateDate(null);
        record.setUpdateDate(new Date());
        userService.updateByPrimaryKey(record);
        System.out.println("更新数据：" + record.getUserId());

        User user = new User();
        user.setUserId(317);
        user.setUserCode(UUID.randomUUID().toString());
        user.setUserName("觅尽尘缘-12345678");
        user.setPassword("my artemis 123");
        user.setGender(0);
        user.setAddress("古希腊");
        user.setAge(99999);
        user.setBirthday(null);
        user.setStatus(1);
        user.setCreateDate(null);
        user.setUpdateDate(new Date());
        userService.updateByPrimaryKeySelective(user);
        System.out.println("更新数据：" + user.getUserId());
    }

    @Test
    public void deleteTest() {
        userService.delete(new User() {{
            setUserName("觅尽尘缘-12345678");
        }});

        userService.deleteByPrimaryKey(318);
    }

    @Test
    public void exampleTest() {
        List<User> users = userService.selectByExample(User.class, 1, "%诸葛%");
        System.out.println(JsonCommon.serializeObject(users));
    }

    @Test
    public void pagingTest() {
        System.out.println(JsonCommon.serializeObject(userService.selectForPageList(1, 10, "USER_ID DESC", new User() {{
            setStatus(0);
        }})));
    }

    @Test
    public void otherTest() {
        System.out.println(JsonCommon.serializeObject(userService.queryForListByStatus(0)));
    }
}
