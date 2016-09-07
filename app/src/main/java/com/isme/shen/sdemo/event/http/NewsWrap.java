package com.isme.shen.sdemo.event.http;

import android.app.Activity;

import com.isme.shen.slibrary.http.Response;
import com.isme.shen.slibrary.http.RetrofitUtils;
import com.isme.shen.slibrary.http.ServiceResponseFun;
import com.isme.shen.slibrary.utils.LogUtils;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by shen on 2016/9/2.
 */
public class NewsWrap extends RetrofitUtils {

    public NewsWrap(Activity activity) {
        super(activity);
    }

    public Observable<NewsDataBean> getAll(String type){
       return RetrofitManager.getInstance().getNewsService()
                .getAll(type)
                .map(new ServiceResponseFun<NewsDataBean>())
                .compose(this.<NewsDataBean>applySchedulers())
                .onErrorResumeNext(new HttpResponseFunc<NewsDataBean>());
    }

    private class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            //ExceptionEngine为处理异常的驱动器
            LogUtils.d("retrofit","HttpResponseFunc...."+throwable.getMessage());
            return Observable.error(throwable);
        }
    }
}