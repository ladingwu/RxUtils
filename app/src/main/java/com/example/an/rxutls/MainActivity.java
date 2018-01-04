package com.example.an.rxutls;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wu.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (name.equals("TextView")) {
            return new TextView(context, attrs);
        }
        return super.onCreateView(name, context, attrs);
    }

    public void onClick(View view) {
        // 注意，这个是设置所有的computation线程的优先级为 4
        RxUtils.setComputationThreadPriority(4);
//        RxUtils.setIoThreadPriority(4);
        Observable.interval(2,2, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //
                        int pro=Thread.currentThread().getPriority();
                        String name=Thread.currentThread().getName();
                        String name2=Thread.currentThread().getName();
                        return aLong;
                    }
                })
                // 这个是设置当前的线程的优先级为7
                .compose(RxUtils.<Long>curThreadPriority(7))
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        int pro=Thread.currentThread().getPriority();
                        String name=Thread.currentThread().getName();
                        String name2=Thread.currentThread().getName();
                        return aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<Long>bindLife(this))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                        Log.i("TAG","conten ---------> "+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
