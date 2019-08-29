package org.lnson.artemis.web.controller;

import org.lnson.artemis.entity.User;
import org.lnson.artemis.model.DataResult;
import org.lnson.artemis.rpc.service.RpcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManageController {

    @Autowired
    private RpcUserService rpcUserService;

    @GetMapping("/")
    public String index() {
        return "hello world";
    }

    @GetMapping("/get")
    public DataResult get()
    {
        return rpcUserService.get(2);
    }

    @GetMapping("/list")
    public DataResult list() {
        User user = new User();
        user.setStatus(1);
        user.setUserName("test");
        return rpcUserService.list(user);
    }

    @GetMapping("/put")
    public DataResult put() {
        User user = new User();
        user.setStatus(1);
        user.setUserName("test");
        return rpcUserService.put(user);
    }

    @GetMapping("/post")
    public DataResult post() {
        User record=new User();
        return rpcUserService.post(record);
    }

    @GetMapping("/delete")
    public DataResult delete() {
        return rpcUserService.delete(1);
    }
}
