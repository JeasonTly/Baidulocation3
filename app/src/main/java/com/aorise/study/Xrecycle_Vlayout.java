package com.aorise.study;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.aorise.study.databinding.ActivityXlayoutBinding;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/28.
 */
public class Xrecycle_Vlayout extends AppCompatActivity implements BaseRefreshListener {
    ActivityXlayoutBinding mDataBinding;
    Banner BannerView;
    private List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBannerData();

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_xlayout);
        mDataBinding.pulllayout.setRefreshListener(this);
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        List<DelegateAdapter.Adapter> mAdapterList = new LinkedList<>();
        mDataBinding.xrecycle.setAdapter(delegateAdapter);
        mDataBinding.xrecycle.setLayoutManager(layoutManager);

        // final List<LayoutHelper> helpers = new LinkedList<>();
        /**
         * A:@tuliyuan 添加banner的布局 @{
         */
        LinearLayoutHelper mLinearLayoutHelper = new LinearLayoutHelper();
        mLinearLayoutHelper.setMargin(10,20,10,10);
        SubAdapter mSub = new SubAdapter(this , mLinearLayoutHelper,1){
            @Override
            public void onViewRecycled(MainViewHolder holder) {
//                if (holder.itemView instanceof ViewPager) {
//                    ((ViewPager) holder.itemView).setAdapter(null);
//                }
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
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                    //设置图片加载器
                    banner.setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context.getApplicationContext())
                                    .load(path)
                                    .into(imageView);
                        }
                    });
                    //设置图片集合
                    banner.setImages(images);
                    //设置banner动画效果
                    banner.setBannerAnimation(Transformer.DepthPage);
                    //设置标题集合（当banner样式有显示title时）
                    banner.setBannerTitles(titles);
                    //设置自动轮播，默认为true
                    banner.isAutoPlay(true);
                    //设置轮播时间
                    banner.setDelayTime(3000);
                    //设置指示器位置（当banner模式中有指示器时）
                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                }

            }

        };
        mAdapterList.add(mSub);

        /**
         * A:@tuliyuan 添加banner的布局 @}
         */


        /**
         * A:@tuliyuan 添加grid的布局 @{
         */
        GridLayoutHelper layoutHelper = new GridLayoutHelper(4);
        layoutHelper.setAutoExpand(true);
        layoutHelper.setItemCount(8);
        mAdapterList.add(new SubAdapter(this,layoutHelper,layoutHelper.getItemCount()));
        /**
         * A:@tuliyuan 添加grid的布局 @}
         */
        /**
         * A:@tuliyuan 添加列表布局 @{
         */
        /**
         * A:@tuliyuan 添加列表布局 @{
         */
        LinearLayoutHelper list = new LinearLayoutHelper();
        list.setMargin(0,10,0,1);
        mAdapterList.add(new SubAdapter(this , list,17,
                new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)));
        /**
         * A:@tuliyuan 添加列表布局 @}
         */
        /**
         * A:@tuliyuan add finally just one line
         *
         */
        delegateAdapter.setAdapters(mAdapterList);

        mDataBinding.xrecycle.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });
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
    }


    @Override
    public void refresh() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {

                }

            }
        }.start();
        mDataBinding.pulllayout.finishRefresh();
        mDataBinding.xrecycle.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void loadMore() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        }.start();
        mDataBinding.pulllayout.finishLoadMore();
        mDataBinding.xrecycle.getAdapter().notifyDataSetChanged();
    }

    static class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;


        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.text, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;
        private ViewDataBinding viewDataBinding;
        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }



        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
}
