package org.lnson.artemis.launch.controller;

import org.lnson.artemis.common.GenerateCommon;
import org.lnson.artemis.entity.User;
import org.lnson.artemis.launch.hystrix.HystrixCommandWrapper;
import org.lnson.artemis.launch.hystrix.HystrixObservableCommandWrapper;
import org.lnson.artemis.model.DataResult;
import org.lnson.artemis.service.UserMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

@RestController
@RequestMapping("/rpc")
public class RpcUserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapperService userService;

    @GetMapping("/get/{id}")
    public DataResult get(@PathVariable("id") Integer no) {
        return new HystrixCommandWrapper(
                () -> new DataResult("1", "success", userService.selectForObject(no)),
                () -> new DataResult("0", "failure", "哥失败了：get")
        ).execute();
    }

    @PostMapping("/list")
    public DataResult list(@RequestBody User record) {
        try {
            return new HystrixCommandWrapper(
                    () -> new DataResult("1", "success", userService.selectForObject(record)),
                    () -> new DataResult("0", "failure", "哥异常了：list")
            ).queue().get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(GenerateCommon.printException(e));
            return new DataResult("0", "failure", null);
        }
    }

    @PostMapping("/post")
    public DataResult post(@RequestBody User record) {
        return new HystrixCommandWrapper(
                () -> new DataResult("1", "success", userService.insert(record)),
                () -> new DataResult("0", "failure", "哥超时了:post")
        ).execute();
    }

    @PostMapping("/put")
    public DataResult put(@RequestBody User record) {
        return new HystrixCommandWrapper(
                () -> new DataResult("1", "success", userService.updateByPrimaryKey(record)),
                () -> new DataResult("0", "failure", "哥熔断了：put")
        ).execute();
    }

    @PostMapping("/delete/{no}")
    public DataResult delete(@PathVariable("no") Integer no) {
        return new HystrixCommandWrapper(
                () -> new DataResult("1", "success", userService.deleteByPrimaryKey(no)),
                () -> new DataResult("0", "failure", "哥降级了：delete")
        ).execute();
    }

    @PostMapping("/mul")
    public List<DataResult> doActionBatch(@RequestBody List<Integer> ids) {
        ArrayList<Supplier<DataResult>> actionArray = new ArrayList<>();
        ArrayList<Supplier<DataResult>> fallActionArray = new ArrayList<>();
        for (Integer id : ids) {
            actionArray.add(() -> new DataResult("1", "success", userService.deleteByPrimaryKey(id)));
            fallActionArray.add(() -> new DataResult("0", "failure", "批量操作异常：" + id));
        }
        Observable<DataResult> observe = new HystrixObservableCommandWrapper(actionArray, fallActionArray).observe();
        ArrayList<DataResult> dataResults = new ArrayList<>();
        Iterator<DataResult> iterator = observe.toBlocking().getIterator();
        while (iterator.hasNext()) {
            dataResults.add(iterator.next());
        }
        return dataResults;
    }
}
