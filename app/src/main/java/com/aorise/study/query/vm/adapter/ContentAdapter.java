package com.aorise.study.query.vm.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.aorise.study.BR;
import com.aorise.study.base.LogT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class ContentAdapter<T> extends RecycleComboAdapter {
    private List<T> contentDatas = new ArrayList<>();
    public ContentAdapter(Context context, int resid, List<T> datas) {
        super(context, resid, datas);
    }

    public void setDatas(List<T> contentDatas ) {
        this.contentDatas.clear();
        this.contentDatas.addAll(contentDatas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return super.onCreateViewHolder(viewGroup, position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int position) {
        super.onBindViewHolder(viewHolder, position);
        getDataBinding().setVariable(BR.titletext,contentDatas.get(position));
        getDataBinding().executePendingBindings();//必须加入这个更新数据
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
