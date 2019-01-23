package com.aorise.study;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.district.DistrictSearch;

public class BDLocationOpreator {
    private static final String TAG = "BDLocationOpreator";
    private Context mContext;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private DistrictSearch mDistrictSearch ;

    public BDLocationOpreator(Context context ,MapView mapView) {
        this.mContext = context ;
        this.mMapView = mapView;
    }
    public boolean getCurrentLoacation(){
        //定位初始化
        mLocationClient = new LocationClient(mContext);
//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(100*1000);
        option.setIsNeedAddress(true);
//设置locationClientOption
        mLocationClient.setLocOption(option);
//注册LocationListener监听器
        mLocationClient.registerLocationListener(new MyLocationListener());
        mBaiduMap.setTrafficEnabled(true);
//开启地图定位图层
        mLocationClient.start();
        return false;
    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            Log.d(TAG, "onReceiveLocation: latitude(经度）  "+ latitude + " longitude（纬度） " +longitude);
            LatLng mLatlng = new LatLng(latitude,longitude);
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(mLatlng)
                    .zoom(18)
                    .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
            mBaiduMap.animateMapStatus(mMapStatusUpdate);

            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

}
