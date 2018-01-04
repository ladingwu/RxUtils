package com.wu.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by wuzhao on 2017/12/31.
 */

 class RxThreadProcity<T>  implements ObservableTransformer<T, T> {
    private int priority=5;
    private static final String KEY_IO_PRIORITY = "rx2.io-priority";


    protected RxThreadProcity(int priority){
        this.priority=priority;
    }
    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.map(new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {
                Thread.currentThread().setPriority(priority);
                return t;
            }
        });
    }



}
