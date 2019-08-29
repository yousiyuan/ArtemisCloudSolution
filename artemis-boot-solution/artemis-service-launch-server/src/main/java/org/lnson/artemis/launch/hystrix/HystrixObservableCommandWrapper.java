package org.lnson.artemis.launch.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.lnson.artemis.model.DataResult;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.function.Supplier;

public class HystrixObservableCommandWrapper extends HystrixObservableCommand<DataResult> {

    private final List<Supplier<DataResult>> funcArguments;
    private final List<Supplier<DataResult>> fallActionArguments;

    public HystrixObservableCommandWrapper(List<Supplier<DataResult>> actionArray, List<Supplier<DataResult>> fallActionArray) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(new Exception().getStackTrace()[1].getClassName()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(new Exception().getStackTrace()[1].getMethodName()))
        );
        this.funcArguments = actionArray;
        this.fallActionArguments = fallActionArray;
    }

    /**
     * 执行逻辑
     */
    @Override
    protected Observable<DataResult> construct() {
        return Observable.create((Subscriber<? super DataResult> subscriber) -> {
            try {
                if (!subscriber.isUnsubscribed()) {
                    for (Supplier<DataResult> func : funcArguments) {
                        subscriber.onNext(func.get());
                    }
                    subscriber.onCompleted();
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 服务降级
     */
    @Override
    protected Observable<DataResult> resumeWithFallback() {
        return Observable.create((Subscriber<? super DataResult> subscriber) -> {
            for (Supplier<DataResult> fallAction : fallActionArguments) {
                subscriber.onNext(fallAction.get());
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
}
