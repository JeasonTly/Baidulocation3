package com.aorise.study.base;

import com.aorise.study.network.HttpRequest;
import com.aorise.study.network.basebean.Result;
import com.aorise.study.network.basebean.StudentInfo;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class BaseRquestListenerImpl implements BaseRquestListener {
    private StudentInfo mStudentInfo;
    @Override
    public void loadStudentInfo(String id,final BaseLoadListener baseLoadListener ,boolean refresh) {
        HttpRequest.init().sendCode("13246788930")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String result) {
//                        mStudentInfo = result.getData();
                        LogT.d(" String "  + result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseLoadListener.loadFailure(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if(true){
                            baseLoadListener.loadmoreCompleted(mStudentInfo);
                        }else{

                        }

                    }
                });


    }
}
