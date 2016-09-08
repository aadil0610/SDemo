package com.isme.shen.sdemo.event.http.fresco;


import com.isme.shen.slibrary.http.ApiException;

import java.util.List;

import rx.functions.Func1;

/**
 * 预处理类，用来预先处理返回数据的状态
 * Created by shen on 2016/9/2.
 */
public class ServiceResponseFun<T> implements Func1<Response<T>, List<T>> {

    @Override
    public List<T> call(Response<T> tResponse) {
        if(200 != tResponse.code){
            throw new ApiException("not current response code :"+tResponse.code);
        }
        return tResponse.getNewslist();
    }
}
