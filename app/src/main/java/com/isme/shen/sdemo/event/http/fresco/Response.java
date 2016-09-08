package com.isme.shen.sdemo.event.http.fresco;


import java.util.List;

/**
 * 统一解析泛型类
 * Created by shen on 2016/9/2.
 */
public class Response<T> {
    public int code;
    public String msg;
    public List<T> newslist;

    public Response(int code, String msg, List<T> newslist) {
        this.code = code;
        this.msg = msg;
        this.newslist = newslist;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<T> newslist) {
        this.newslist = newslist;
    }
}
