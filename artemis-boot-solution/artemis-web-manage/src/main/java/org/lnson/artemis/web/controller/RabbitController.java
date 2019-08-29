package org.lnson.artemis.web.controller;

import org.lnson.artemis.rabbit.service.GoodsProduceService;
import org.lnson.artemis.rabbit.service.OrderProduceService;
import org.lnson.artemis.rabbit.service.UserProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/mq")
public class RabbitController {

    @Autowired
    private UserProduceService userProduceService;

    @Autowired
    private GoodsProduceService goodsProduceService;

    @Autowired
    private OrderProduceService orderProduceService;

    @GetMapping("/user/{msg}")
    public String sendUserInfo(@PathVariable("msg") String message) {
        userProduceService.sendQueueMessage(message);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " >> 已发送用户信息";
    }

    @GetMapping("/goods/{msg}")
    public String sendGoodsInfo(@PathVariable("msg") String message) {
        goodsProduceService.sendQueueMessage(message);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " >> 已发送货物信息";
    }

    @GetMapping("/order/{msg}")
    public String sendOrderInfo(@PathVariable("msg") String message) {
        orderProduceService.sendQueueMessage(message);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " >> 已发送订单信息";
    }

}
