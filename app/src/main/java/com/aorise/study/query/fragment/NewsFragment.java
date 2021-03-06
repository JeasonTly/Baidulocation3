package com.aorise.study.query.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aorise.study.R;
import com.aorise.study.base.LogT;
import com.aorise.study.databinding.FragmentNewsBinding;
import com.aorise.study.query.fragment.bean.NewsTitle;
import com.aorise.study.query.fragment.bean.NewsTitleContent;
import com.aorise.study.query.listener.HttpRquestReturnListener;
import com.aorise.study.query.vm.NewsVm;
import com.aorise.study.query.vm.adapter.DataListAdapter;
import com.aorise.study.query.vm.adapter.RecycleItemClick;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class NewsFragment extends Fragment implements BaseRefreshListener, HttpRquestReturnListener, RecycleItemClick {
    private FragmentNewsBinding mDataBinding;
    private List<NewsTitle> datas;
    private List<Integer> pstTab = new ArrayList<>();
    private static int currentPosition = 0 ;

    private NewsVm newsVm;
    private boolean loadmore = false;
    private DataListAdapter dataListAdapter;
    private LinearLayoutManager manager;

    public NewsFragment(){
        super();
        initData();
        newsVm = new NewsVm();
        LogT.d("datasize is " + datas.size());
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
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news,container,false);
        mDataBinding.newsRefresh.setRefreshListener(this);
            for(int i = 0 ;i < datas.size();i++){
                NewsTitle title =  datas.get(i);//这是获取第N 种标签的基础数据的数量
                String titleChar = title.getNewsType();//获取对应标题名称
                pstTab.add(i);
                mDataBinding.tabHost.addTab(mDataBinding.tabHost.newTab().setText(titleChar).setTag(i));
            }
        dataListAdapter = new DataListAdapter(getContext(),this);
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.newsRecycleContent.setLayoutManager(manager);
        mDataBinding.newsRecycleContent.setAdapter(dataListAdapter);
        dataListAdapter.setRefreshData(datas.get(0).getDatas());
        mDataBinding.tabHost.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogT.d("输出数据 position " + tab.getPosition() + " Content is "+ datas.get(tab.getPosition()).getDatas());
                dataListAdapter.setRefreshData(datas.get(tab.getPosition()).getDatas());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return mDataBinding.getRoot();
    }
    private void initData(){

        LogT.d(" initdata");
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
        titleContents4.add(new NewsTitleContent("2014-09-02","x21x" ,"xx21x" , "xxxx31xx"));
        titleContents4.add(new NewsTitleContent("2014-09-03","x21x" ,"xx21x" , "xxxx31xx"));

        List<NewsTitleContent> titleContents5 = new ArrayList<>();
        NewsTitleContent newsTitleContent;
        for(int i =0;i<7;i++){
            newsTitleContent = new NewsTitleContent("2015-10-21","xx" + i ,"x2xx" , "xxxxxx");
            titleContents5.add(newsTitleContent);
        }
        datas = new ArrayList<NewsTitle>();
        datas.add(new NewsTitle(0, "Title1",titleContents1));
        datas.add(new NewsTitle(1, "Title2",titleContents2));
        datas.add(new NewsTitle(3, "Title3",titleContents3));
        datas.add(new NewsTitle(4, "Title4",titleContents4));
        datas.add(new NewsTitle(5, "Title5",titleContents5));
//        LogT.d("添加数据 " + new NewsTitle(0, "Title1",titleContents1));
//        LogT.d("添加数据 " + new NewsTitle(1, "Title1",titleContents2));
//        LogT.d("添加数据 " + new NewsTitle(3, "Title1",titleContents3));
//        LogT.d("添加数据 " + new NewsTitle(4, "Title1",titleContents4));
//        LogT.d("添加数据 " + new NewsTitle(5, "Title1",titleContents5));

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
            mDataBinding.newsRefresh.finishLoadMore();
        }else{
            mDataBinding.newsRefresh.finishRefresh();
        }
    }

    @Override
    public void loadFailuer(String e) {
        if(loadmore){
            mDataBinding.newsRefresh.finishLoadMore();
        }else{
            mDataBinding.newsRefresh.finishRefresh();
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
       // mContentAdapter.setRefrshDatas(datas.get(postion).getDatas());

    }
}
