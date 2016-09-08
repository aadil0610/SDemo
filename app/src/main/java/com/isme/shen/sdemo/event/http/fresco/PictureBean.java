package com.isme.shen.sdemo.event.http.fresco;

import java.util.List;

/**
 * Created by shen on 2016/9/8.
 */
public class PictureBean {


        /* "ctime": "2015-07-17",
            "title": "那个抱走王明涵的，你上微信吗？看完这个你会心软吗？",
            "description": "中国传统文化",
            "picUrl": "http://zxpic.gtimg.com/infonew/0/wechat_pics_-667708.jpg/640",
            "url": "http://mp.weixin.qq.com/s?__biz=MzA3OTg2NjEwNg==&amp;idx=5&amp;mid=209313388&amp;sn=7e30bd2851d22f69580e202c31fc7ecf"*/

    String ctime;
    String title;
    String description;
    String picUrl;
    String url;

    public PictureBean(String ctime, String title, String description, String picUrl, String url) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Newslist{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
