package com.aorise.study.query.fragment.bean;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class NewsTitleContent {
    private String date ;
    private String title;
    private String summary;
    private String imgurl;

    public NewsTitleContent(String date, String title, String summary, String imgurl) {
        this.date = date;
        this.title = title;
        this.summary = summary;
        this.imgurl = imgurl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "NewsTitleContent{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
