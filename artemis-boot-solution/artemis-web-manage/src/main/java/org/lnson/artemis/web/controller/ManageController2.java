package org.lnson.artemis.web.controller;

import org.lnson.artemis.entity.User;
import org.lnson.artemis.model.DataResult;
import org.lnson.artemis.rpc.service.RpcUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
public class ManageController2 {

    @Autowired
    private RpcUserService2 rpcUserService2;

    @GetMapping("/get")
    public DataResult get() {
        return rpcUserService2.get(12);
    }

    @GetMapping("/list")
    public DataResult list() {
        User user = new User();
        user.setStatus(0);
        return rpcUserService2.list(user);
    }
}
