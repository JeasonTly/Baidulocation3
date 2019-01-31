package com.aorise.study;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.aorise.study.adapter.SubAdapter;
import com.aorise.study.adapter.VlayoutAdapter;
import com.aorise.study.base.IBaseView;
import com.aorise.study.base.NVVM_Student;
import com.aorise.study.databinding.ActivityXlayoutBinding;
import com.aorise.study.loader.GlideApp;

import com.aorise.study.network.basebean.StudentInfo;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Tuliyuan.
 * Date: 2019/1/28.
 */
public class Xrecycle_Vlayout extends AppCompatActivity implements BaseRefreshListener, IBaseView {

    private static final String TAG = "Xrecycle_Vlayout";

    //====================recycleView Vlayout模式所用变量===============

    private DelegateAdapter delegateAdapter ;
    private VirtualLayoutManager mVirtualLayoutManager ;
    List<DelegateAdapter.Adapter> mAdapterList = new LinkedList<>();
    //====================recycleView Vlayout模式所用变量===============

    //====================DataBindingView变量===========================
    ActivityXlayoutBinding mDataBinding;
    //====================Handler主线程更新UI===========================
    private Handler mHandler;
    private final int MSG_UPDATE_DATA_LOADMORE = 1001;//加载数据
    private final int MSG_UPDATE_DATA_REFRESH = 1002;//刷新数据

    private NVVM_Student nvvm_student;
    /**
     * Oncreate函数初始化
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        nvvm_student = new NVVM_Student(this,delegateAdapter,this);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_UPDATE_DATA_REFRESH:
                        mDataBinding.pulllayout.finishRefresh();
                        mDataBinding.xrecycle.getAdapter().notifyDataSetChanged();
                        Toast.makeText(Xrecycle_Vlayout.this," Refresh Success" ,Toast.LENGTH_SHORT).show();
                        break;
                    case MSG_UPDATE_DATA_LOADMORE:
                        mDataBinding.pulllayout.finishLoadMore();
                        mDataBinding.xrecycle.getAdapter().notifyDataSetChanged();
                        Toast.makeText(Xrecycle_Vlayout.this," LoadMore Success" ,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }


    private void initView(){
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_xlayout);
        mDataBinding.pulllayout.setRefreshListener(this);


        //=============================================初始化自定义layout的RecycleView=========================
        mVirtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(mVirtualLayoutManager, true);
        mDataBinding.xrecycle.setAdapter(delegateAdapter); //设置委托适配器
        mDataBinding.xrecycle.setLayoutManager(mVirtualLayoutManager); //设置虚拟布局管理器

        //=============================================初始化自定义layout的RecycleView=========================
        /**
         * A:@tuliyuan 添加banner的布局 @{
         */
        LinearLayoutHelper mLinearLayoutHelper = new LinearLayoutHelper();//设置单列装的装载样式
        mLinearLayoutHelper.setMargin(10,20,10,10);//设置边距

        SubAdapter mSub = new SubAdapter(this , mLinearLayoutHelper,1){//设置自定义的列表模式的布局格式！ 数量为1
            @Override
            public void onViewRecycled(MainViewHolder holder) {
            }

            @Override
            public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    return new MainViewHolder(LayoutInflater.from(Xrecycle_Vlayout.this).inflate(R.layout.xrecycle_banner, parent, false));
                }
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

            }

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                if (holder.itemView instanceof Banner) {
                    Banner banner = holder.itemView.findViewById(R.id.banner);
                    nvvm_student.setBanner(banner,position);
                }

            }

        };
        mAdapterList.add(mSub);

        /**
         * A:@tuliyuan 添加banner的布局 @}
         */
        //=============================================

        /**
         * A:@tuliyuan 添加grid的布局 @{
         */
        GridLayoutHelper GridLayoutHelper = new GridLayoutHelper(4);
        //layoutHelper.setSpanCount(4);//设置表格的一行有多少个数据，上面在GridLayoutHelper已经设置
        GridLayoutHelper.setAutoExpand(true); //无法填充满的时候 自动等分划分布局
        GridLayoutHelper.setItemCount(8); //设置传入的数量

        mAdapterList.add(new SubAdapter(this,GridLayoutHelper,GridLayoutHelper.getItemCount()));

        //mAdapterList.add(new VlayoutAdapter(this,GridLayoutHelper,studentInfos));
        /**
         * A:@tuliyuan 添加grid的布局 @}
         */
        //=============================================
        /**
         * A:@tuliyuan 添加列表布局 @{
         */
        LinearLayoutHelper list = new LinearLayoutHelper();//设置单个的列表容器
        list.setMargin(0,10,0,1);//设置边距
        mAdapterList.add(new SubAdapter(this , list,17,
                new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)));
       // mAdapterList.add(new VlayoutAdapter(this,list,studentInfos));
        //设置自定义的列表模式的布局格式！ 数量为17，layoutparam自定义，并且添加进入委托适配器中
        /**
         * A:@tuliyuan 添加列表布局 @}
         */
        /**
         * A:@tuliyuan add finally just one line
         *
         */
        delegateAdapter.setAdapters(mAdapterList);///委托适配器获取各样式的适配器进行装载与数据统计

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
    }


    @Override
    public void refresh() {
        nvvm_student.updateData(true);
        Log.d(TAG, "loadStart: refresh");
    }

    @Override
    public void loadMore() {
        nvvm_student.updateData(false);
        Log.d(TAG, "loadStart: loadMore");
    }

    @Override
    public void loadStart(boolean refresh) {
        Log.d(TAG, "loadStart: refresh ? " + refresh);
    }

    @Override
    public void loadComplete(boolean refresh) {
        Log.d(TAG, "loadStart: loadComplete " );
        mDataBinding.pulllayout.finishRefresh();
        mDataBinding.pulllayout.finishLoadMore();
    }

    @Override
    public void loadFailure(String message ,boolean refresh) {
        Log.d(TAG, "loadStart: loadFailure  " +message);
        Message msg = new Message();
        msg.what = MSG_UPDATE_DATA_LOADMORE;
        if(refresh){
            msg.what = MSG_UPDATE_DATA_REFRESH;
        }
        mHandler.sendMessageDelayed(msg,2000);
    }
}
