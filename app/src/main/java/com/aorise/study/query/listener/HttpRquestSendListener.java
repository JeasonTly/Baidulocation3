package com.aorise.study.query.listener;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public interface HttpRquestSendListener {
    void QueryAllData(boolean loadmore);
    void QueryTypeData(String Newstype,boolean loadmore);
}
