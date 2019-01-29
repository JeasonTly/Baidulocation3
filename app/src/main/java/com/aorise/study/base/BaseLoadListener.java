package com.aorise.study.base;

import com.aorise.study.network.basebean.StudentInfo;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/28.
 */
public interface BaseLoadListener {
    /**
     * A@:tuliyuan 数据刷新完成
     */
    void refreshCompleted();
    /**
     * A@:tuliyuan 数据加载完成
     */
    void loadmoreCompleted(StudentInfo studentInfo);
    /**
     * A@:tuliyuan 数据获取异常
     */
    void loadFailure(String err);
}
