package org.lnson.artemis.rpc.service;

import org.lnson.artemis.entity.User;
import org.lnson.artemis.model.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "ARTEMIS-SERVICE-LAUNCH-SERVER", path = "/rpc", contextId = "rpc")
public interface RpcUserService2 {

    @GetMapping("/get/{id}")
    public DataResult get(@PathVariable("id") Integer no);

    @PostMapping("/list")
    public DataResult list(@RequestBody User record);

    @PostMapping("/post")
    public DataResult post(@RequestBody User record);

    @PostMapping("/put")
    public DataResult put(@RequestBody User record);

    @PostMapping("/delete/{no}")
    public DataResult delete(@PathVariable("no") Integer no);

    @PostMapping("/mul")
    public List<DataResult> doActionBatch(@RequestBody List<Integer> ids);

}
