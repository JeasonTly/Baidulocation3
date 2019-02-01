package com.aorise.study.query.vm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aorise.study.BR;
import com.aorise.study.R;
import com.aorise.study.adapter.BaseViewHolder;
import com.aorise.study.base.LogT;
import com.aorise.study.query.fragment.bean.NewsTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class TitleAdapter extends RecycleComboAdapter<NewsTitle> {

    private ViewDataBinding mDataBinding;


    private int currentPosition = 0;
    private RecycleItemClick mRecycleViewItemClick;
    public TitleAdapter(Context context,RecycleItemClick mRecycleViewItemClick) {
        super(context);
        this.mRecycleViewItemClick = mRecycleViewItemClick;

    }


    @Override
    public BaseViewHolder onCreateVH(ViewGroup viewGroup, int i) {
         mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.title,viewGroup,false);
        return new BaseViewHolder(mDataBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder vh, final int position) {
        LogT.d("ddd");
        TextView textView = vh.itemView.findViewById(R.id.news_type_title);
        mDataBinding.setVariable(BR.titletext,getDatas().get(position).getNewsType());
        mDataBinding.executePendingBindings();
        if(currentPosition == position){
            textView.setTextColor(Color.GREEN);
        }else{
            textView.setTextColor(Color.BLUE);
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;
                mRecycleViewItemClick.onTitleItemClick(position);
                notifyDataSetChanged();
            }
        });
    }
}
