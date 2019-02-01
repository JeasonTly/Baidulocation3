package com.aorise.study.query.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aorise.study.BR;
import com.aorise.study.R;
import com.aorise.study.adapter.BaseViewHolder;
import com.aorise.study.base.LogT;
import com.aorise.study.databinding.ActivityQueryNewsBinding;
import com.aorise.study.query.fragment.bean.NewsTitle;
import com.aorise.study.query.fragment.bean.NewsTitleContent;
import com.aorise.study.query.listener.HttpRquestReturnListener;
import com.aorise.study.query.vm.NewsVm;
import com.aorise.study.query.vm.adapter.ContentAdapter;
import com.aorise.study.query.vm.adapter.RecycleItemClick;
import com.aorise.study.query.vm.adapter.TitleAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class NewsFragment extends Fragment implements BaseRefreshListener, HttpRquestReturnListener, RecycleItemClick {
    private ActivityQueryNewsBinding mDataBinding;
    private List<NewsTitle> datas = new ArrayList<NewsTitle>();
    private ContentAdapter mContentAdapter;
    private static int currentPosition = 0 ;
    private Context mContext;
    private NewsVm newsVm;
    private boolean loadmore = false;
    private TitleAdapter mTitleAdapter ;
    public NewsFragment (){
        super();
        List<NewsTitleContent> titleContents1 = new ArrayList<>();
        titleContents1.add(new NewsTitleContent("2018-11-21","xx" ,"xxx" , "xxxxxx"));
        titleContents1.add(new NewsTitleContent("2018-11-11","x1x" ,"xx2x" , "xxxx1xx"));
        titleContents1.add(new NewsTitleContent("2018-11-01","x11x" ,"xx23x" , "xxxx11xx"));
        List<NewsTitleContent> titleContents2 = new ArrayList<>();
        titleContents2.add(new NewsTitleContent("2011-10-21","xx" ,"x2xx" , "xxxxxx"));
        titleContents2.add(new NewsTitleContent("2011-10-11","x2x" ,"xx4x" , "xxxx1xx"));
        titleContents2.add(new NewsTitleContent("2011-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents2.add(new NewsTitleContent("2011-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        List<NewsTitleContent> titleContents3 = new ArrayList<>();
        titleContents3.add(new NewsTitleContent("2013-10-21","xx" ,"x2xx" , "xxxxxx"));
        List<NewsTitleContent> titleContents4 = new ArrayList<>();
        titleContents4.add(new NewsTitleContent("2014-10-21","xx" ,"x2xx" , "xxxxxx"));
        titleContents4.add(new NewsTitleContent("2014-10-11","x2x" ,"xx4x" , "xxxx1xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        List<NewsTitleContent> titleContents5 = new ArrayList<>();
        titleContents5.add(new NewsTitleContent("2015-10-21","xx" ,"x2xx" , "xxxxxx"));
        titleContents5.add(new NewsTitleContent("2015-10-11","x2x" ,"xx4x" , "xxxx1xx"));
        titleContents5.add(new NewsTitleContent("2015-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-01","x21x" ,"xx21x" , "xxxx31xx"));

        datas.add(new NewsTitle(0, "Title1",titleContents1));
        datas.add(new NewsTitle(1, "Title2",titleContents2));
        datas.add(new NewsTitle(3, "Title3",titleContents3));
        datas.add(new NewsTitle(4, "Title4",titleContents4));
        datas.add(new NewsTitle(5, "Title5",titleContents5));
        newsVm = new NewsVm();

        LogT.d("datasize is "+datas.size());
    }
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.activity_query_news,container,false);
        mDataBinding.newsPullRefresh.setRefreshListener(this);
        mContext = getContext();
        initRecycle();
        return mDataBinding.getRoot();

    }
    private void initRecycle(){
        if(datas != null && mDataBinding != null ){
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
            mTitleAdapter = new TitleAdapter(mContext,this);

            ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.HORIZONTAL);
            mDataBinding.newsRecycleviewTitle.setLayoutManager(manager);
            mDataBinding.newsRecycleviewTitle.setAdapter(mTitleAdapter);
            mTitleAdapter.setRefrshDatas(datas);
            RecyclerView.LayoutManager ListManager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) ListManager).setOrientation(LinearLayoutManager.VERTICAL);
            mContentAdapter = new ContentAdapter(mContext);
            mDataBinding.newsRecycleview.setLayoutManager(ListManager);
            mDataBinding.newsRecycleview.setAdapter(mContentAdapter);
            mContentAdapter.setRefrshDatas(datas.get(0).getDatas());
        }
    }
    @Override
    public void refresh() {
        LogT.d("refresh data");
        newsVm.QueryTypeData(datas.get(currentPosition).getNewsType(),false);
        loadmore = false;
    }

    @Override
    public void loadMore() {
        LogT.d("loadMore data");
        newsVm.QueryTypeData(datas.get(currentPosition).getNewsType(),true);
        loadmore = true;
    }

    @Override
    public void loadSuccess() {
        if(loadmore){
            mDataBinding.newsPullRefresh.finishLoadMore();
        }else{
            mDataBinding.newsPullRefresh.finishRefresh();
        }
    }

    @Override
    public void loadFailuer(String e) {
        if(loadmore){
            mDataBinding.newsPullRefresh.finishLoadMore();
        }else{
            mDataBinding.newsPullRefresh.finishRefresh();
        }
    }


    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onTitleItemClick(int postion) {
        LogT.d("position is " + postion + " get datas is " + datas.get(postion).getDatas());
        mContentAdapter.setRefrshDatas(datas.get(postion).getDatas());

    }
}
