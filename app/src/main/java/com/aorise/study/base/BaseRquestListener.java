package com.aorise.study.base;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public interface BaseRquestListener {
    public void loadStudentInfo(String id , BaseLoadListener baseLoadListener ,boolean refresh);
}
