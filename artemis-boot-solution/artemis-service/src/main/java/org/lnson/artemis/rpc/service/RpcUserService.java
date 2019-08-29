package org.lnson.artemis.rpc.service;

import org.lnson.artemis.entity.User;
import org.lnson.artemis.model.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ARTEMIS-SERVICE-LAUNCH-SERVER", path = "/user", contextId = "user")
public interface RpcUserService {

    @GetMapping("/get/{id}")
    public DataResult get(@PathVariable("id") Integer no);

    @PostMapping("/list")
    public DataResult list(@RequestBody User record);

    @PostMapping("/post")
    public DataResult post(@RequestBody User record);

    @PostMapping("/put")
    public DataResult put(@RequestBody User record);

    @PostMapping("/delete")
    public DataResult delete(Integer no);

}
