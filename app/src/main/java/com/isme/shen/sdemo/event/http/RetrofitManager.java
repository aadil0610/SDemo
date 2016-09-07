package com.isme.shen.sdemo.event.http;

import android.content.Context;

import com.isme.shen.sdemo.Const;
import com.isme.shen.slibrary.http.RetrofitManagerAbs;

/**
 * Created by shen on 2016/9/2.
 */
public class RetrofitManager extends RetrofitManagerAbs {

    private NewsService newsService;
    private static RetrofitManager instance;

    private RetrofitManager() {
        super();
        init(Const.NEWS_URL);
    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null)
                    instance = new RetrofitManager();
            }
        }
        return instance;
    }

    public NewsService getNewsService() {
        if (newsService == null) {
            newsService = getApiService(NewsService.class);
        }
        return newsService;
    }
}
