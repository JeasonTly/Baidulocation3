package com.aorise.study.toolbar.bean;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.aorise.study.base.LogT;
import com.aorise.study.network.HttpRequest;
import com.aorise.study.network.basebean.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tuliyuan.
 * Date: 2019/5/7.
 */
public class TestLiveData extends LiveData<Location> {
    private static TestLiveData sInstance;
    private LocationManager locationManager;
    private Context mContext;

    public static TestLiveData getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TestLiveData(context);
        }
        return sInstance;
    }

    public TestLiveData(Context context) {
        locationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
        mContext = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        HttpRequest.init().requestMovies("https://api.douban.com/v2/movie/top250?start=0&count=10").enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    LogT.d(" response successful ");
                    Movie movie = response.body();
                    List<Movie.SubjectsBean> movies = movie.getSubjects();
                    for (Movie.SubjectsBean mo : movies) {
                        LogT.d("Movie Title is " + mo.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        locationManager.removeUpdates(listener);
    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setValue(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
