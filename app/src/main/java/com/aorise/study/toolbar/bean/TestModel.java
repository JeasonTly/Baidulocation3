package com.aorise.study.toolbar.bean;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Tuliyuan.
 * Date: 2019/5/7.
 */
public class TestModel extends ViewModel {
    private MutableLiveData<String> status;

    public MutableLiveData<String> getStatus() {
        if(status == null){
            status = new MutableLiveData<>();
        }
        return status;
    }
}
