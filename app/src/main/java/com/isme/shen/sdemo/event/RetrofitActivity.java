package com.isme.shen.sdemo.event;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import com.isme.shen.sdemo.BaseButtonListActivity;
import com.isme.shen.sdemo.event.http.NewsDataBean;
import com.isme.shen.sdemo.event.http.NewsWrap;
import com.isme.shen.slibrary.http.ApiException;
import com.isme.shen.slibrary.http.ServiceSubscribe;
import com.isme.shen.slibrary.utils.LogUtils;
import com.isme.shen.slibrary.utils.ToastUtils;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shen on 2016/8/31.
 */
public class RetrofitActivity extends BaseButtonListActivity {


    private String[] events = {"normal_1", "normal_2", "rxjava"};
    private String url = "http://japi.juhe.cn";
    String topKey = "60be1d130b36f659c8c63772a109fd14";

    @Override
    protected int getCount() {
        return events.length;
    }

    @Override
    protected String getBtnName(int position) {
        return events[position];
    }

    @Override
    protected void onClick(int position) {
        switch (position) {
            case 0:
                normal_1();
                break;
            case 1:
                normal_2();
                break;
            case 2:
                rxjava();
                break;
            default:
                break;
        }
    }

    private void rxjava() {
       /* RetrofitManager.getInstance().getNewsService()
                .getAll("top")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceSubscribe<NewsDataBean>(this) {
                    @Override
                    public void onNext(NewsDataBean newsDataBean) {
                        ToastUtils.showShort(RetrofitActivity.this,"news:"+newsDataBean.getResult().getData().get(0).toString());
                    }
                });*/
        new NewsWrap(this).getAll("yule")
                .subscribe(new ServiceSubscribe<NewsDataBean>(this){
                    @Override
                    public void onNext(NewsDataBean newsDataBean) {
                        LogUtils.d("retrofit",Looper.myLooper().getThread().getName()+":news:"+newsDataBean.getData().get(0).toString()+";");
                        ToastUtils.showShort(RetrofitActivity.this,"news:"+newsDataBean.getData().get(0).toString());
                    }
                });

        /*RetrofitManager.getInstance().getNewsService().getAll("top")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<NewsDataBean>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.d("11","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("11","onError:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Response<NewsDataBean> newsDataBeanResponse) {
                        LogUtils.d("11","onNext:"+newsDataBeanResponse.toString());
                        LogUtils.d("11","onNext:"+newsDataBeanResponse.getT().getData());
                    }
                });*/

      /*  RetrofitManager.getInstance()
                .getNewsService().getAll2("top")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDataBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.d("22","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("22","onError:"+e.getMessage());
                    }

                    @Override
                    public void onNext(NewsDataBean newsDataBeanResponse) {
                        LogUtils.d("22","onNext:"+newsDataBeanResponse.getResult().getData().get(0).toString());
                    }
                });*/

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.NEWS_URL)
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new StringConverterFactory())
                .build();

        NewsService newsService = retrofit.create(NewsService.class);
        newsService.getAll2("top")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NewsDataBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.d("22","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("22","onError:"+e.getMessage());
                    }

                    @Override
                    public void onNext(NewsDataBean newsDataBean) {
                        LogUtils.d("22","onNext:"+newsDataBean.getResult().getData().get(0).toString());
//                        LogUtils.d("22","onNext:"+newsDataBean);
                    }
                });*/
    }

    /**
     * 自定义ConverterFactory
     */
    private void normal_2() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InternetServerImpl internetServer = retrofit.create(InternetServerImpl.class);

        Call<String> call = internetServer.getStringStr("2016-8-31", topKey);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Intent intent = new Intent(RetrofitActivity.this, EventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detail_result", "" + response.body());
                intent.putExtras(bundle);
                RetrofitActivity.this.startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    /**
     * 基本get方法
     */
    private void normal_1() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InternetServerImpl internetServer = retrofit.create(InternetServerImpl.class);

        Call<DataBean> call = internetServer.getString("2016-8-31", topKey);

        call.enqueue(new Callback<DataBean>() {
            @Override
            public void onResponse(Call<DataBean> call, Response<DataBean> response) {
                Intent intent = new Intent(RetrofitActivity.this, EventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detail_result", response.body().getResult().getData().toString());
                intent.putExtras(bundle);
                RetrofitActivity.this.startActivity(intent);
            }

            @Override
            public void onFailure(Call<DataBean> call, Throwable t) {

            }
        });
    }
}
