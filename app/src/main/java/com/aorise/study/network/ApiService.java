package com.aorise.study.network;

import com.aorise.study.network.basebean.Movie;
import com.aorise.study.network.basebean.Result;
import com.aorise.study.network.basebean.StudentInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public interface ApiService {
    @GET("api/base/studeninfo")
    Observable<Result<StudentInfo>> getStudentInfo(@Query("id") String id);

    @POST("SendCode")
    Observable<String> sendCode(@Query("phone") String phone);

    @GET
    Call<Movie> requestMovies(@Url String url);
}
