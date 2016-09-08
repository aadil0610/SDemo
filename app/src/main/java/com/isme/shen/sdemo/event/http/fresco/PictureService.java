package com.isme.shen.sdemo.event.http.fresco;

import com.isme.shen.sdemo.Const;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shen on 2016/9/8.
 */
public interface PictureService {

    String pPath = "/meinv/?key="+ Const.PICTURE_KEY;
    @GET(pPath)
    Observable<Response<PictureBean>> obtainPictures(@Query("num")int num);

}
