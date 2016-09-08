package com.isme.shen.sdemo.event.http.retrofit;

import com.isme.shen.sdemo.Const;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shen on 2016/9/1.
 */
public interface NewsService {

    String newsUrlPath = "/toutiao/index?key="+ Const.NEWS_KEY;
    @GET(newsUrlPath)
    Observable<Response<NewsDataBean>> getAll(@Query("type") String type);

}
