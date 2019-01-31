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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class ContentAdapter<T> extends RecycleComboAdapter {
    private List<T> contentDatas = new ArrayList<>();
    private ViewDataBinding mDataBinding;
    public ContentAdapter(Context context, List<T> datas) {
        super(context, datas);
        contentDatas = datas;
    }

    public void setDatas(List<T> contentDatas ) {
        this.contentDatas.clear();
        this.contentDatas.addAll(contentDatas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateVH(@NonNull ViewGroup viewGroup, int position) {
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.title,viewGroup,false);
        return new BaseViewHolder(mDataBinding);
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder viewHolder,final int position) {
        mDataBinding.setVariable(BR.titletext,contentDatas.get(position).toString());
        mDataBinding.executePendingBindings();//必须加入这个更新数据
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogT.d("点击了内容中的" + contentDatas.get(position)+ "中的第" + position + "项目");
            }
        });
    }

    @Override
    protected ViewDataBinding getDataBinding() {
        return super.getDataBinding();
    }

    @Override
    public int getItemCount() {
        return contentDatas.size();
    }
}
