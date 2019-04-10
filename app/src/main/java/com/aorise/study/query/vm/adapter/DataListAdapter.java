package com.aorise.study.query.vm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aorise.study.BR;
import com.aorise.study.R;
import com.aorise.study.adapter.BaseViewHolder;
import com.aorise.study.base.LogT;
import com.aorise.study.query.fragment.bean.NewsTitleContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/2/1.
 */
public class DataListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<NewsTitleContent> datas = new ArrayList<>();
    private ViewDataBinding mDataBinding;
    private Context mContext;
    private RecycleItemClick recycleItemClick;
    public DataListAdapter(Context context ,RecycleItemClick recycleItemClick) {
       // this.datas = datas;
        this.recycleItemClick = recycleItemClick;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.cloud_home_item,viewGroup,false);
        return new BaseViewHolder(mDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder,final int i) {
        LogT.d("data is "+ datas.get(i));
        //一定要获取baseviewHolder的databinding对象，因为是要针对对应的databinding进行设置
        baseViewHolder.getVBinding().setVariable(BR.newscontent,datas.get(i));
        baseViewHolder.getVBinding().executePendingBindings();
        baseViewHolder.getVBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleItemClick.onTitleItemClick(i);
            }
        });
    }

    public void setRefreshData(List<NewsTitleContent> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
}
