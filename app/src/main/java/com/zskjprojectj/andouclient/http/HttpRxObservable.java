package com.zskjprojectj.andouclient.http;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HttpRxObservable {
    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 无管理生命周期,容易导致内存溢出
     */
    public static <T> Observable<BaseResult<T>> getObservable(Observable<BaseResult<T>> apiObservable) {
        Observable<BaseResult<T>> observable = apiObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }


    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider自动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity.../RxFragment...
     */
    public static <T> Observable<BaseResult<T>> getObservable(Observable<BaseResult<T>> apiObservable, LifecycleProvider<ActivityEvent> lifecycle) {
        Observable<BaseResult<T>> observable;

        if (lifecycle != null) {
            observable = apiObservable
                    //.compose(lifecycle.bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<ActivityEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity,RxAppCompatActivity,RxFragmentActivity
     */
    public static <T> Observable<BaseResult<T>> getObservable(Observable<BaseResult<T>> apiObservable, LifecycleProvider<ActivityEvent> lifecycle, ActivityEvent event) {
        Observable<BaseResult<T>> observable;
        if (lifecycle != null) {
            observable = apiObservable
                   // .compose(lifecycle.bindUntilEvent(event))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }


    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     */
    public static <T> Observable<BaseResult<T>> getObservable(Observable<BaseResult<T>> apiObservable, LifecycleProvider<FragmentEvent> lifecycle, FragmentEvent event) {
        Observable<BaseResult<T>> observable;
        if (lifecycle != null) {
            observable = apiObservable
                    //.compose(lifecycle.bindUntilEvent(event))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            observable = getObservable(apiObservable);
        }
        return observable;
    }
}
