package com.aorise.study.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class HttpRequest {
    private static final long DEFAULT_TIME_OUT = 8; //Second
    public static ApiService init(){
        Retrofit mBuilder=  new Retrofit.Builder()
                //.baseUrl(HttpBaseURL.BASE_URL)
                .baseUrl("https://api.douban.com/v2/movie/")
                .client(getHttpClient().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
       return mBuilder.create(ApiService.class);
    }
    private static OkHttpClient.Builder getHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS);//读操作 超时时间
        builder.retryOnConnectionFailure(true);//错误重连
        return builder;
    }


}
