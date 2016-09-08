package com.isme.shen.sdemo.event.http.okhttp;


import com.isme.shen.sdemo.event.http.okhttp.DataBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by shen on 2016/8/31.
 */
public interface InternetServerImpl {

    @GET("/calendar/day")
    Call<DataBean> getString(@Query("date") String date, @Query("key")String key);

    @GET("/calendar/day")
    Call<String> getStringStr(@Query("date") String date, @Query("key")String key);
}


