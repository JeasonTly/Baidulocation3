package com.aorise.study.base;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.aorise.study.R;
import com.aorise.study.Xrecycle_Vlayout;
import com.aorise.study.loader.GlideApp;
import com.aorise.study.network.basebean.StudentInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class NVVM_Student implements BaseLoadListener {
    private Context mContext;
    private DelegateAdapter mDelegateAdapter;
    private BaseRquestListener mBaseRquestListenner;
    private IBaseView iBaseView;
    private boolean state_refresh;
    //==============Test==========================
    private List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<StudentInfo> studentInfos = new ArrayList<>(); ///TEST
    //==============Test==========================
    public NVVM_Student(Context context, DelegateAdapter delegateAdapter,IBaseView baseView) {
        this.mContext = context;
        this.mDelegateAdapter = delegateAdapter;
        iBaseView = baseView;
        mBaseRquestListenner = new BaseRquestListenerImpl();
    }

    private void initBannerData(){
        images.add(R.drawable.drawable_01);
        images.add(R.drawable.drawable_02);
        images.add(R.drawable.drawable_03);
        images.add(R.drawable.drawable_04);
        images.add(R.drawable.drawable_05);
        titles.add("Title 1");
        titles.add("Title 2");
        titles.add("Title 3");
        titles.add("Title 4");
        titles.add("Title 5");

        studentInfos.add(new StudentInfo("tly",23,"男","14"));
        studentInfos.add(new StudentInfo("mxg",23,"nv","14"));
        studentInfos.add(new StudentInfo("abc",13,"男","12"));
        studentInfos.add(new StudentInfo("xx",25,"女","14"));
        studentInfos.add(new StudentInfo("tly",23,"男","14"));
    }

    @Override
    public void refreshCompleted() {
        iBaseView.loadComplete(state_refresh);
        mDelegateAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadmoreCompleted(StudentInfo studentInfo) {
        iBaseView.loadComplete(state_refresh);
        mDelegateAdapter.notifyDataSetChanged();
    }


    @Override
    public void loadFailure(String err) {
       // mDelegateAdapter.notifyDataSetChanged();
        iBaseView.loadFailure(err,state_refresh);
    }

    public void updateData(boolean refresh){
        state_refresh = refresh;
        iBaseView.loadStart(refresh);
        if(refresh){
            mBaseRquestListenner.loadStudentInfo("1",this,state_refresh);
        }else{
            mBaseRquestListenner.loadStudentInfo("2",this ,state_refresh);
        }
    }
    public void setBanner(Banner banner, final int position){
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                GlideApp.with(mContext)//注意传递的上下文 不一样使用的方法与接口也不一致
                        .load(path)
                        .placeholder(R.drawable.drawable_01)//占位符，如果表示网络请求图片失败时将显示此图片
                        .error(R.drawable.drawable_02)// 失败符。如果网络请求永久失败将显示此图
                        .centerCrop() //设置imageview scaltype
                        .into(imageView);
            }
        });
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(mContext," Press image " + (position +1),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
