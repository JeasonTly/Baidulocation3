package com.aorise.study.base;


/**
 * 作者： 周旭 on 2017年10月19日 0019.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public interface IBaseView {

    /**
     * 实际项目中可能还有其他的接口，此处写出来便于拓展
     */
    /**
     * 开始加载
     *
     * @param refresh 加载的类型 0：第一次记载 1：下拉刷新 2：上拉加载更多
     */
    void loadStart(boolean refresh);

    /**
     * 加载完成
     */
    void loadComplete(boolean refresh);

    /**
     * 加载失败
     *
     * @param message
     */
    void loadFailure(String message ,boolean refresh);
}
