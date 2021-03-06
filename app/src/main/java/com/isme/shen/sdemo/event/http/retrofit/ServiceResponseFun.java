package com.isme.shen.sdemo.event.http.retrofit;


import com.isme.shen.slibrary.http.ApiException;

import rx.functions.Func1;

/**
 * 预处理类，用来预先处理返回数据的状态
 * Created by shen on 2016/9/2.
 */
public class ServiceResponseFun<T> implements Func1<Response<T>, T> {

    @Override
    public T call(Response<T> tResponse) {
        if (!"成功的返回".equals(tResponse.getReason())) {
            throw new ApiException(ApiException.DATA_STYLE_ERROR,tResponse.getReason(),tResponse.getReason());
        }
        T t = tResponse.getResult();
        return tResponse.getResult();
    }
}
