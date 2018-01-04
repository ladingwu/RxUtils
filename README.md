# RxUtils

## 一些RxJava的小工具

- 通过RxJava初步实现了Android的LiveData的功能

```
        Observable.interval(2,2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                // 这个调用可以保证只有在onResume之后，才会收到数据
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
```

- 提供设置Rx线程优先级的能力

设置一类线程的优先级：

```
        // 注意，这个是设置所有的computation线程的优先级为 4,
        //只需要调用一次,必须在使用RxJava代码之前调用，最好在Application中设置                                  
        RxUtils.setComputationThreadPriority(4);
        
        // 设置所有IO线程的优先级
        RxUtils.setIoThreadPriority(4);
        
```

设置当前的线程的优先级:

```
        Observable.interval(2,2, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //
                        int pro=Thread.currentThread().getPriority();
                        String name=Thread.currentThread().getName();
                        
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
                      
                        return aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
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
```
