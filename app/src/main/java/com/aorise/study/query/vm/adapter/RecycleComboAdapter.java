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
import com.aorise.study.base.LogT;

import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public abstract class RecycleComboAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> datas;
    private ViewDataBinding mDataBinding;
    public Context mContext;
    private boolean NO_DATAS = false;
    private static final int VIEW_TYPE_NO_DATAS = 8302;
    public RecycleComboAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.datas = datas;
        LogT.d("data size "+datas.size());
        if(datas == null && datas.size() == 0){
            NO_DATAS = true;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(NO_DATAS){
            LogT.d("no datas");
            mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.no_datas,viewGroup,false);
            return new BaseViewHolder(mDataBinding);
        }else{
            return onCreateVH(viewGroup,i);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int i) {
        onBindVH(viewHolder,i);
    }
    public abstract BaseViewHolder onCreateVH(ViewGroup viewGroup, int i);
    public abstract void onBindVH(BaseViewHolder vh, int i);
    protected ViewDataBinding getDataBinding(){
        return mDataBinding;
    }

    @Override
    public int getItemCount() {
        if(datas == null && datas.size() == 0){
            return 1;
        }
        return 0;
    }
}
