package com.aorise.study.query.vm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aorise.study.R;
import com.aorise.study.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class RecycleComboAdapter<T> extends RecyclerView.Adapter {

    public List<T> datas;
    private int resid ;
    private ViewDataBinding mDataBinding;
    private Context mContext;
    private static int VIEW_TYPE = -1;
    private static final int VIEW_TYPE_NO_DATAS = 0;
    public RecycleComboAdapter(Context context, @LayoutRes int resid , List<T> datas) {
        this.mContext = context;
        this.datas = datas;
        this.resid = resid;
        if(datas.size() == 0){
            VIEW_TYPE = VIEW_TYPE_NO_DATAS ;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(VIEW_TYPE == VIEW_TYPE_NO_DATAS){
            mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.no_datas,viewGroup,false);
            return new BaseViewHolder(mDataBinding);
        }
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),resid,viewGroup,false);
        return new BaseViewHolder(mDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
    protected ViewDataBinding getDataBinding(){
        return mDataBinding;
    }

    @Override
    public int getItemCount() {
        if(datas.size()==0){
            return 1;
        }
        return datas.size();
    }

    public List<T> getDatas() {
        return datas;
    }
}
