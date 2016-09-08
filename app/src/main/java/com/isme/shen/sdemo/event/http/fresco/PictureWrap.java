package com.isme.shen.sdemo.event.http.fresco;

import android.app.Activity;

import com.isme.shen.sdemo.event.http.retrofit.*;
import com.isme.shen.slibrary.http.RetrofitUtils;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by shen on 2016/9/8.
 */
public class PictureWrap extends RetrofitUtils{


    public PictureWrap(Activity activity) {
        super(activity);
    }

    public Observable<List<PictureBean>> obtainPictures(int num) {
        return RetrofitManager.getInstance().getPictureServicec()
                .obtainPictures(num)
                .map(new ServiceResponseFun<PictureBean>())
                .compose(this.<List<PictureBean>>applySchedulers());
    }
}
