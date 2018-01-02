package com.wu.utils;

import android.arch.lifecycle.LifecycleOwner;

import io.reactivex.ObservableTransformer;

/**
 * Created by wuzhao on 2018/1/2.
 */

public class RxUtils {
    public static <T>ObservableTransformer<T, T> curThreadPriority(int priority){
        return new RxThreadProcity<>(priority);
    }

    public static <T> ObservableTransformer<T, T> bindLife(LifecycleOwner owner){
        return new RxLife<>(owner);
    }

    public static void  setIoThreadPriority(int  priority){
        String KEY_IO_PRIORITY = "rx2.io-priority";
        System.setProperty(KEY_IO_PRIORITY,String.valueOf(priority));

    }
    public static void  setComputationThreadPriority(int  priority){
        String KEY_IO_PRIORITY = "rx2.computation-priority";
        System.setProperty(KEY_IO_PRIORITY,String.valueOf(priority));

    }
    public static void  setNewThreadPriority(int  priority){
        String KEY_IO_PRIORITY = "rx2.newthread-priority";
        System.setProperty(KEY_IO_PRIORITY,String.valueOf(priority));

    }
    public static void  setSingleThreadPriority(int  priority){
        String KEY_IO_PRIORITY = "rx2.single-priority";
        System.setProperty(KEY_IO_PRIORITY,String.valueOf(priority));

    }
}
