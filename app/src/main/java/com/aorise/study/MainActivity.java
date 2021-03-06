package com.aorise.study;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.aorise.autocompletesearch.SearchAdapter;
import com.aorise.study.QRCode.QRCreateActivity;
import com.aorise.study.contact.ContactsActivity;
import com.aorise.study.query.Main2Activity;
import com.aorise.study.query.QueryActivity;
import com.aorise.study.toolbar.bean.TestLiveData;
import com.aorise.study.toolbar.bean.TestModel;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnGetDistricSearchResultListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private DistrictSearch mDistrictSearch ;
    private AutoCompleteTextView mAutoCompleteTextView;
    private double Currentlatitude , CurrentLongtitude;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,};
    List<String> mPermissionList = new ArrayList<>();
    private Button btn1;
    private TestModel mTestModel;
    private TestLiveData mTestLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.baidumap);
        btn1 = (Button)findViewById(R.id.btn1);
        String[] datas = getResources().getStringArray(R.array.region);
        Log.d(this.getClass().getName(), "onCreate: datas "+datas.length);
        mAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.auto);
        mAutoCompleteTextView.setAdapter(new SearchAdapter<String>(this,android.R.layout.simple_list_item_1,datas,-1));
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);
        initLocation();
        if (Build.VERSION.SDK_INT > 23) {
            mPermissionList.clear();//清空没有通过的权限
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);//添加还未授予的权限
                }
            }

            //申请权限
            if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
                ActivityCompat.requestPermissions(this, permissions, 2040);
            } else {
                //说明权限都已经通过，可以做你想做的事情去
            }
        }
        //显示卫星图层
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);ACCESS_FINE_LOCATION
        initVariable();

    }
    private void initVariable(){
        final Observer<Location> statusObserver = new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                btn1.setText(location.getLatitude() +"--" +location.getLongitude());
            }
        };
//        mTestModel = ViewModelProviders.of(this).get(TestModel.class);
//        mTestModel.getStatus().observe(this,statusObserver);
        mTestLiveData = TestLiveData.getInstance(this);
        mTestLiveData.observe(this,statusObserver);

    }
    private void initArea(){
        mDistrictSearch = DistrictSearch.newInstance();

        mDistrictSearch.setOnDistrictSearchListener(this);
        mDistrictSearch.searchDistrict(new DistrictSearchOption()
                .cityName("怀化市")
                .districtName("鹤城区"));
    }

    private void initLocation(){
        //定位初始化
        mLocationClient = new LocationClient(this);
        LatLng GEO_HUAIHUA = new LatLng(27.5, 109.95);
        MapStatusUpdate status2 = MapStatusUpdateFactory.newLatLng(GEO_HUAIHUA);
        mBaiduMap.setMapStatus(status2);
        mBaiduMap.setTrafficEnabled(true);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(10f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(100*1000);//设置扫描周期 100s
       // option.setIsNeedAddress(true);
//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();

    }
    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        Log.d("11111", "onGetDistrictResult: districtResult "+ districtResult.error);
       // if (null != districtResult && districtResult.error != SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            //获取边界坐标点，并展示
            if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
                List<List<LatLng>> polyLines = districtResult.getPolylines();
                if (polyLines == null) {
                    return;
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (List<LatLng> polyline : polyLines) {
                    OverlayOptions ooPolyline11 = new PolylineOptions().width(10)
                            .points(polyline).dottedLine(true);
                    mBaiduMap.addOverlay(ooPolyline11);
                    OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                            .stroke(new Stroke(5, 0xFF6587FF)).fillColor(0xFFFFFF);
                    mBaiduMap.addOverlay(ooPolygon);
                    for (LatLng latLng : polyline) {
                        builder.include(latLng);
                    }
                }
                mBaiduMap.setMapStatus(MapStatusUpdateFactory
                        .newLatLngBounds(builder.build()));//设置当前位置信息

            }
      //  }
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mDistrictSearch.destroy();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
//        mTestModel.getStatus().setValue("onResume++++");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void setAutoText(String str){
        mAutoCompleteTextView.setText(str);
    }
    public void onclick(View view) {
        //initArea();
        Intent mIntent = new Intent(this, Main3Activity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
    public void onclick2(View view) {
        //initArea();
        Intent mIntent = new Intent(this, QueryActivity.class);
        mIntent.putExtra("intent_fragment","");
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
    public void onclick3(View view){
        //initArea();
        Intent mIntent = new Intent(this, CustomViewActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
    public void onclick4(View view){
        //initArea();
        Intent mIntent = new Intent(this, ContactsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
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
            LatLng mLatlng = new LatLng(latitude,longitude);
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(mLatlng)
                    .zoom(18)
                    .build();
//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
            mBaiduMap.animateMapStatus(mMapStatusUpdate);

            Log.d("1111", "onReceiveLocation: latitude  "+ latitude + " longitude " +longitude);

            Button mButton = new Button(MainActivity.this);
            mButton.setBackgroundResource(R.drawable.ic_launcher_foreground);
            mButton.setText("Umex");
            InfoWindow mInforWindow = new InfoWindow(mButton,mLatlng,-10);
            mBaiduMap.showInfoWindow(mInforWindow);
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
