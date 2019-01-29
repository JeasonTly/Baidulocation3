package com.aorise.study.network;

import com.aorise.study.network.basebean.Result;
import com.aorise.study.network.basebean.StudentInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public interface ApiService {
    @GET("api/base/studeninfo")
    Observable<Result<StudentInfo>> getStudentInfo(@Query("id") String id);
}
