package com.aorise.study.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.aorise.study.R;
import com.aorise.study.base.BaseLoadListener;

import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/28.
 */
public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder> extends DelegateAdapter.Adapter<VH> {

    public Context mContext;
    public LayoutHelper mLayoutHelper ;
    public List<T> datas;
    @LayoutRes
    public static int resid = R.layout.text;;
    public VirtualLayoutManager.LayoutParams mLayoutParams;
    public LayoutInflater inflater;

    /**
     * 使用默认的 LinearLayoutHelper 和
     * @param context
     * @param datas
     */
    public BaseAdapter(Context context, List<T> datas){
       // this(context,new LinearLayoutHelper(),datas);
        this.mLayoutHelper = new LinearLayoutHelper();
        this.mContext = context;
        this.datas = datas;
        inflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public BaseAdapter(Context context, LayoutHelper layoutHelper, List<T> datas) {
        this(context,datas);
        this.mLayoutHelper = layoutHelper;
    }
    //携带虚拟布局管理器布局参数的基础构造函数
    public BaseAdapter(Context context, LayoutHelper layoutHelper, List<T> datas, VirtualLayoutManager.LayoutParams layoutParams) {
        this(context,layoutHelper,datas);
        this.mLayoutParams = layoutParams;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateVH(parent,viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if(mLayoutParams != null){
            holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }
        onBindVH(holder, position);
    }


    @Override
    protected void onBindViewHolderWithOffset(VH holder, final int position, int offsetTotal) {
        onBindVHoffset(holder,position,offsetTotal);
    }
    /**
     * 创建 View Holder
     *
     * @param parent   parent
     * @param viewType item type
     * @return view holder
     */
    public abstract VH onCreateVH(ViewGroup parent, int viewType);

    /**
     * 绑定 View Holder
     *
     * @param vh       view holder
     * @param position position
     */
    public abstract void onBindVH(VH vh, int position);
    protected abstract void onBindVHoffset(VH viewHolder, int position, int offsetTotal);
    @Override
    public int getItemCount() {

        if(datas != null ){
            return datas.size();
        }else {
            return 0;
        }
    }
    /**
     * 刷新数据
     *
     * @param data 数据源
     */
    public void refreshData(List<T> data) {
        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param data 加载的新数据
     */
    public void loadMoreData(List<T> data) {
        datas.addAll(data);
        notifyDataSetChanged();
    }


}




