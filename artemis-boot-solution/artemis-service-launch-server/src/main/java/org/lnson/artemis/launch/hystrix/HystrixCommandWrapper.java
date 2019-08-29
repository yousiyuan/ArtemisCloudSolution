package org.lnson.artemis.launch.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesCommandDefault;
import org.lnson.artemis.model.DataResult;

import java.util.function.Supplier;

public class HystrixCommandWrapper extends HystrixCommand<DataResult> {

    private final Supplier<DataResult> func;
    private final Supplier<DataResult> fallFunc;

    public HystrixCommandWrapper(Supplier<DataResult> func, Supplier<DataResult> fallFunc) {
        // 特别说明下，groupKey、commandKey以及ThreadPoolKey请认真思考一些有意义的名字用来设置，
        // 此处这么玩是为了偷懒，每次都传参很lower，实际运用中请从思考从配置文件中读取配置，
        // 为了对不同的API进行更细粒度的配置，你甚至可以考虑对这个类进行二次包装
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(Thread.currentThread().getStackTrace()[1].getClassName()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(Thread.currentThread().getStackTrace()[1].getMethodName()))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(Thread.currentThread().getThreadGroup().getName())));

        // 配置相关
        // HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000)
        // new HystrixPropertiesCommandDefault(HystrixCommandKey.Factory.asKey(Thread.currentThread().getStackTrace()[1].getMethodName()), HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000));
        // 这位兄弟辛苦了，写得挺详细
        // https://blog.csdn.net/andyLiuzy/article/details/82349756
        this.func = func;
        this.fallFunc = fallFunc;
    }

    @Override
    protected DataResult run() throws Exception {
        return func.get();
    }

    @Override
    protected DataResult getFallback() {
        return fallFunc.get();
    }

}
