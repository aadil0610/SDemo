package com.isme.shen.sdemo.event;

import android.content.Intent;
import android.os.Bundle;

import com.isme.shen.sdemo.BaseButtonListActivity;
import com.isme.shen.slibrary.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shen on 2016/8/31.
 */
public class OkHttpActivity extends BaseButtonListActivity {

    String[] events = {"normal_get"};
    private String url = "http://v.juhe.cn/toutiao/index";
    private String topKey = "8cb01a61511199da226ffad84d35491b";
    private Intent intent;
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

        switch (position){
            case 0:
                normal_get();
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }

    }

    private void normal_get() {

        String uu = url+"?type=top&key="+topKey;
        OkHttpClient okHttpClient = new OkHttpClient();

        String uuu = "http://v.juhe.cn/toutiao/index?type=top&key=8cb01a61511199da226ffad84d35491b";
        ToastUtils.showShort(this,"url:"+uu);
        Request request = new Request.Builder()
                .url(uuu)
                .build();
        Call call = okHttpClient.newCall(request);
        //同步操作
       /* try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(OkHttpActivity.this,"onFailure:"+e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                intent = new Intent(OkHttpActivity.this,EventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("detail_result",response.body().string());
                intent.putExtras(bundle);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpActivity.this.startActivity(intent);
                    }
                });
            }
        });
    }
}
