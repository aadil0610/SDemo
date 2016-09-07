package com.isme.shen.slibrary.http;


import rx.functions.Func1;

/**
 * 预处理类，用来预先处理返回数据的状态
 * Created by shen on 2016/9/2.
 */
public class ServiceResponseFun<T> implements Func1<Response<T>, T> {

    @Override
    public T call(Response<T> tResponse) {
        if (!"成功的返回".equals(tResponse.getReason())) {
            throw new ApiException(tResponse.getReason());
        }
        T t = tResponse.getResult();
        return tResponse.getResult();
    }
}
