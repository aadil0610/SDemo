package com.isme.shen.sdemo.event.http;

import com.isme.shen.slibrary.http.Response;

import java.util.List;

/**
 * Created by shen on 2016/9/1.
 */
/*
public class NewsDataBean {

//    public int state;
    public String reason;
    public Result result;

    public NewsDataBean(String reason, Result result) {
        this.reason = reason;
        this.result = result;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
*/

    public class NewsDataBean {
        String stat;
        List<Data> data;

        public NewsDataBean(String stat, List<Data> data) {
            this.stat = stat;
            this.data = data;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public class Data {
            String title;
            String date;
            String author_name;
            String thumbnail_pic_s;
            String thumbnail_pic_s02;
            String thumbnail_pic_s03;
            String url;
            String uniquekey;
            String type;
            String realtype;

            public Data(String title, String date, String author_name, String thumbnail_pic_s, String thumbnail_pic_s02, String thumbnail_pic_s03, String url, String uniquekey, String type, String realtype) {
                this.title = title;
                this.date = date;
                this.author_name = author_name;
                this.thumbnail_pic_s = thumbnail_pic_s;
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
                this.url = url;
                this.uniquekey = uniquekey;
                this.type = type;
                this.realtype = realtype;
            }

            @Override
            public String toString() {
                return "Data{" +
                        "title='" + title + '\'' +
                        ", date='" + date + '\'' +
                        ", author_name='" + author_name + '\'' +
                        ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                        ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                        ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                        ", url='" + url + '\'' +
                        ", uniquekey='" + uniquekey + '\'' +
                        ", type='" + type + '\'' +
                        ", realtype='" + realtype + '\'' +
                        '}';
            }
        }
    }

