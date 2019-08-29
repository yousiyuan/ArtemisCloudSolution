package org.lnson.artemis.launch.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.lnson.artemis.entity.User;
import org.lnson.artemis.model.DataResult;
import org.lnson.artemis.service.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
@DefaultProperties(defaultFallback = "hystrixFallBack")
public class UserController {

    @Autowired
    private UserMapperService userService;

    @GetMapping("/get/{id}")
    public DataResult get(@PathVariable("id") Integer no) {
        return new DataResult("1", "success", userService.selectForObject(no));
    }

    @PostMapping("/list")
    @HystrixCommand(commandProperties = {/*设置超时时长*/@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    public DataResult list(@RequestBody User record) {
        return new DataResult("1", "success", userService.selectForList(record));
    }

    @PostMapping("/post")
    @HystrixCommand
    public DataResult post(@RequestBody User record) {
        userService.insert(record);
        return new DataResult("1", "success", record.getUserId());
    }

    @PostMapping("/put")
    @HystrixCommand(fallbackMethod = "updateFallBack",
            commandProperties = {
                    // 熔断开启
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    // 设置时间窗口中请求断路的最小请求数，在时间窗口中接收到10个失败的请求，将会熔断微服务的调用，快速返回"错误"的响应信息
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 每10秒重试(采样周期)
                    // 熔断后的重试时间窗口，且在该时间窗口内只允许一次重试。
                    // 即在熔断开关打开后，在该时间窗口允许有一次重试，
                    // 如果重试成功，则将重置Health采样统计并闭合熔断开关实现快速恢复，否则熔断开关还是打开状态，执行快速失败。
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    // 如果在一个采样时间窗口内，失败率超过该配置，则自动打开熔断开关实现降级处理，即快速失败。
                    // 默认配置下采样周期为10s，失败率为50%。(此处配置为失败率为60%)
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
            })
    public DataResult put(@RequestBody User record) {
        return new DataResult("1", "success", userService.updateByPrimaryKey(record));
    }

    @PostMapping("/delete")
    @HystrixCommand
    public DataResult delete(Integer no) {
        return new DataResult("1", "success", userService.deleteByPrimaryKey(no));
    }


    // 统一服务降级处理
    public DataResult hystrixFallBack(Throwable throwable) {
        return new DataResult("0", "failure", "统一服务降级处理" + System.currentTimeMillis());
    }

    // 查询列表异常的服务降级处理
    public DataResult updateFallBack(User record) {
        return new DataResult("0", "failure", "运行时异常导致服务降级执行" + new Date());
    }

}
