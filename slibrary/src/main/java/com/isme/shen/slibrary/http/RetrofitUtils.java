package com.isme.shen.slibrary.http;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import com.isme.shen.slibrary.utils.DialogUtils;
import com.isme.shen.slibrary.utils.LogUtils;
import com.isme.shen.slibrary.utils.NetUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/9/2.
 */
public class RetrofitUtils {

    protected Activity activity;
    protected RetrofitUtils(Activity activity){
        this.activity = activity;
    }

    private Observable.Transformer transformer;
    /**
     * @param <T>
     * @return
     */
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        if(transformer == null){
            transformer = new Observable.Transformer<T, T>() {
                @Override
                public Observable<T> call(Observable<T> tObservable) {
                    return tObservable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    DialogUtils.getInstance().showProgressDialog(activity);
                                    LogUtils.d("retrofit",Looper.myLooper().getThread().getName()+":"+NetUtils.isConnected(activity.getApplicationContext()));
                                    if(!NetUtils.isConnected(activity.getApplicationContext()) && !NetUtils.isWifi(activity.getApplicationContext())){
                                        ApiException apiException = new ApiException(ApiException.NET_DISCONNECT,"网络未连接");
                                        throw apiException;
                                    }
                                }
                            }).subscribeOn(AndroidSchedulers.mainThread());
                }
            };
        }
        LogUtils.d("retrofit","applySchedulers...");
        return transformer;
    }
}
