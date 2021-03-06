package com.aorise.study.query.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aorise.study.R;
import com.aorise.study.base.LogT;
import com.aorise.study.databinding.FragmentListBinding;
import com.aorise.study.query.fragment.bean.NewsTitleContent;
import com.aorise.study.query.vm.adapter.DataListAdapter;
import com.aorise.study.query.vm.adapter.RecycleItemClick;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Tuliyuan.
 * Date: 2019/2/11.
 */
public class ListFragment extends Fragment implements RecycleItemClick {
    private FragmentListBinding fragmentListBinding;
    private DataListAdapter dataListAdapter;
    private LinearLayoutManager manager;

    public static List<NewsTitleContent> getContents() {
        return contents;
    }

    private static List<NewsTitleContent> contents = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogT.d(""+getTag());

    }

    public void setDatas(List<NewsTitleContent> datas){
        if(dataListAdapter != null){
            contents.clear();
            contents.addAll(datas);
            dataListAdapter.setRefreshData(datas);
        }else{
            LogT.d("");
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogT.d("");
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container,false);
        dataListAdapter = new DataListAdapter(getContext(),this);
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentListBinding.newsRecycleContent.setLayoutManager(manager);
        fragmentListBinding.newsRecycleContent.setLoadingMoreEnabled(true);
        fragmentListBinding.newsRecycleContent.setAdapter(dataListAdapter);
        fragmentListBinding.newsRecycleContent.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Stack stack = new Stack();
        stack.push(contents);
        dataListAdapter.setRefreshData(contents);
        LogT.d("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTitleItemClick(int postion) {
        LogT.d(" click data is "+contents.get(postion));
    }
}
