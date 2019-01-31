package com.aorise.study.query.listener;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public interface HttpRquestReturnListener {
    void loadSuccess();
    void loadFailuer(String e);
}
