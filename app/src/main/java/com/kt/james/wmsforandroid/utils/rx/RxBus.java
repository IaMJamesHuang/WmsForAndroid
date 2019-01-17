package com.kt.james.wmsforandroid.utils.rx;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

public class RxBus {

    private FlowableProcessor<Object> mBus;

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    //指定接受类型
    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    //指定code，不需要新建类
    public <T> Flowable<T> tFlowable(Class<T> eventType, final int code) {
        return mBus.ofType(RxBusMessage.class)
                .filter(new Predicate<RxBusMessage>() {
                    @Override
                    public boolean test(RxBusMessage rxBusMessage) throws Exception {
                        return rxBusMessage.getCode() == code && eventType.isInstance(rxBusMessage.getObject());
                    }
                }).map(new Function<RxBusMessage, T>() {
                    @Override
                    public T apply(RxBusMessage rxBusMessage) throws Exception {
                        return (T) rxBusMessage.getObject();
                    }
                }).cast(eventType);
    }

    //全面发射
    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

}
