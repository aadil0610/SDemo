package com.isme.shen.slibrary.http;

import android.app.Application;

import com.isme.shen.slibrary.utils.LogUtils;
import com.isme.shen.slibrary.utils.NetUtils;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.Response;


/**
 * Created by shen on 2016/9/2.
 */
public class OkHttpManager {

    private static OkHttpManager okHttpManager;
    private OkHttpClient.Builder builder;

    private OkHttpManager(){
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.readTimeout(20,TimeUnit.SECONDS);
        builder.writeTimeout(20,TimeUnit.SECONDS);
        builder.addInterceptor(new LoggingInterceptor());
    }

    public static OkHttpManager getInstance(){
        if(okHttpManager == null){
            synchronized (OkHttpManager.class){
                if(okHttpManager == null){
                    okHttpManager = new OkHttpManager();
                }
            }
        }
        return okHttpManager;
    }

    public OkHttpClient getBuilder(){
        return builder.build();
    }


    private class LoggingInterceptor implements okhttp3.Interceptor {

        @Override
        public okhttp3.Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
            long tStart = System.nanoTime();
            Request request = chain.request();
            LogUtils.i("OkHttpManager","Sending request on :"+request.url().toString());
            okhttp3.Response response = chain.proceed(request);
            MediaType contentType = null;
            String bodyString = null;
            if (response.body() != null) {
                contentType = response.body().contentType();
                bodyString = response.body().string();
            }
            long tOver = System.nanoTime();
            LogUtils.i("OkHttpManager","Received response on :"+request.url().toString());
            LogUtils.i("OkHttpManager","Received bodystring:"+bodyString);
            if (response.body() != null) {
                // 深坑！
                // 打印body后原ResponseBody会被清空，需要重新设置body
                ResponseBody body = ResponseBody.create(contentType, bodyString);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }
}
