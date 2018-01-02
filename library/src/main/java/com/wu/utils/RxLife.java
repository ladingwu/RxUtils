package com.wu.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by wuzhao on 2018/1/1.
 */

 class RxLife<T>  implements ObservableTransformer<T, T>, LifecycleObserver {
    private LifecycleOwner mLifecycleOwner;
    private final PublishSubject<T> subject=PublishSubject.create();
//    private BehaviorSubject<T> subject=BehaviorSubject.create();

    private T curData;
    private Disposable mDisposable;
    protected RxLife(LifecycleOwner owner) {
        mLifecycleOwner=owner;
    }




    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        if (mLifecycleOwner.getLifecycle().getCurrentState()== Lifecycle.State.DESTROYED){
            return Observable.empty();
        }else{
            mLifecycleOwner.getLifecycle().addObserver(this);
            upstream.subscribe(new Observer<T>() {


                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable=d;
                }

                @Override
                public void onNext(T t) {
//                    subject.onNext(t);
                    val++;
                    curData=t;
                    sendDataIfNeed();
                }

                @Override
                public void onError(Throwable e) {
                    subject.onError(e);
                }

                @Override
                public void onComplete() {
                    subject.onComplete();
                }
            });

            return subject;
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny() {
        if (mLifecycleOwner.getLifecycle().getCurrentState()== Lifecycle.State.RESUMED) {
            if (curData != null) {
                subject.onNext(curData);
            }
        }else if (mLifecycleOwner.getLifecycle().getCurrentState()== Lifecycle.State.DESTROYED){
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    private int val=-1;
    private int lastVal=-1;
    private void sendDataIfNeed(){
        Lifecycle.State curstate=mLifecycleOwner.getLifecycle().getCurrentState();
        if (isActiveState(curstate)) {
            if (val>lastVal) {
                lastVal=val;
                if (mDisposable != null && !mDisposable.isDisposed()) {
                    subject.onNext(curData);
                }
            }
        }
    }

    static boolean isActiveState(Lifecycle.State state) {
        return state.isAtLeast(Lifecycle.State.STARTED);
    }
}
