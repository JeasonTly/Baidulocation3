package com.aorise.study.query.fragment.bean;

import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class NewsTitle<T> {
    private int id ;
    private String newsType;
    private List<T> datas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public NewsTitle(int id, String newsType, List<T> datas) {
        this.id = id;
        this.newsType = newsType;
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "NewsTitle{" +
                "id=" + id +
                ", newsType='" + newsType + '\'' +
                ", datas=" + datas +
                '}';
    }
}
