package org.lnson.artemis.launch.controller;

import org.lnson.artemis.rabbit.service.RabbitProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class CloudStreamController {

    private RabbitProduceService generateRabbitService;
    private RabbitProduceService accountRabbitService;
    private RabbitProduceService bankRabbitService;

    @Autowired
    public CloudStreamController(
            @Qualifier("generateRabbitService") RabbitProduceService generateRabbitService,
            @Qualifier("accountRabbitService") RabbitProduceService accountRabbitService,
            @Qualifier("bankRabbitService") RabbitProduceService bankRabbitService) {
        this.generateRabbitService = generateRabbitService;
        this.accountRabbitService = accountRabbitService;
        this.bankRabbitService = bankRabbitService;
    }

    @GetMapping("/account/send/{msg}")
    public String sendAccountRabbitMessage(@PathVariable("msg") String message) {
        accountRabbitService.send(message);
        return "账户余额：" + message;
    }

    @GetMapping("/bank/send/{msg}")
    public String sendBankRabbitMessage(@PathVariable("msg") String message) {
        bankRabbitService.send(message);
        return "银行客服：" + message;
    }

    // 测试消息转发
    @GetMapping("/transfer/send/{msg}")
    public String sendDefaultRabbitMessage(@PathVariable("msg") String message) {
        generateRabbitService.send(message);
        return "账户主人：" + message;
    }

}
