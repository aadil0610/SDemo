package com.isme.shen.slibrary.http;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 本类作为library使用
 * 项目中自定义管理类实现此类解决接口实例化方法
 * 初始化的url会被保存在局部变量
 * Created by shen on 2016/9/1.
 */
public abstract class RetrofitManagerAbs {

    private static Retrofit retrofit;
    private static String url;

    protected RetrofitManagerAbs() {
    }

    /**
     * 初始化和更换接口或url时使用
     *
     * @param url
     */
    public void init(String url) {
        if (retrofit == null) {//初始化时保存最初值
            this.url = url;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(OkHttpManager.getInstance().getBuilder())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

    protected static <T> T  getApiService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 临时更换链接,用完调用reset方法
    * */
    public void changedUrlTemp(String url){
        init(url);
    }

    /**
     * 恢复为第一次传入的url
     * */
    public void reset(){
        if(url != null&&!"".equals(url)){
            init(url);
        }
    }
}
