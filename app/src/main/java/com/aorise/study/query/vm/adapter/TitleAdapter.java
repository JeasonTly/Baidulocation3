package com.aorise.study.query.vm.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aorise.study.R;
import com.aorise.study.query.fragment.bean.NewsTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class TitleAdapter extends RecycleComboAdapter {

    private List<NewsTitle> titledatas = new ArrayList<>();
    private int currentPosition = -1;
    private ContentAdapter mContentAdapter ;
    public TitleAdapter(Context context, int resid, List<NewsTitle> datas,ContentAdapter contentAdapter) {
        super(context, resid, datas);
        this.titledatas = datas;
        this.mContentAdapter = contentAdapter;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return super.onCreateViewHolder(viewGroup, position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int position) {
        super.onBindViewHolder(viewHolder, position);
        setPosition(position);
        TextView textView = viewHolder.itemView.findViewById(R.id.news_type_title);
        if(currentPosition == position){
            textView.setTextColor(Color.GREEN);
        }else{
            textView.setTextColor(Color.BLUE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LogT.d("点击了标题中的" + " 第 "+ (position+1) + "项" + " datas 为"+datas.get(position).getDatas());
                mContentAdapter.setDatas(titledatas.get(position).getDatas());
                setPosition(position);
                notifyDataSetChanged();
            }
        });

    }
    public List<NewsTitle> getTitledatas() {
        return titledatas;
    }

    @Override
    protected ViewDataBinding getDataBinding() {
        return super.getDataBinding();
    }
    public int getPosition() {
        return currentPosition;
    }

    public void setPosition(int position) {
        this.currentPosition = position;
    }

}
