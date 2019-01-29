package com.aorise.study.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.aorise.study.BR;
import com.aorise.study.R;
import com.aorise.study.network.basebean.StudentInfo;

import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class VlayoutAdapter extends BaseAdapter<StudentInfo,VlayoutViewHolder> {

    public VlayoutAdapter(Context context,List<StudentInfo> datas){
        super(context,datas);
    }
    public VlayoutAdapter(Context context, LayoutHelper layoutHelper, List<StudentInfo> datas) {
        super(context, layoutHelper, datas);
    }
    public VlayoutAdapter(Context context, LayoutHelper layoutHelper, List<StudentInfo> datas, @LayoutRes int resid) {
        super(context, layoutHelper, datas);
        this.resid = resid;
    }
    public VlayoutAdapter(Context context, LayoutHelper layoutHelper, List<StudentInfo> datas, @LayoutRes int layoutres , VirtualLayoutManager.LayoutParams layoutParams) {
        super(context,layoutHelper,datas,layoutParams);
        this.resid = layoutres;
    }

    @Override
    public VlayoutViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, resid , parent, false);
        return new VlayoutViewHolder(dataBinding);
    }

    @Override
    public void onBindVH(VlayoutViewHolder vlayoutViewHolder, int position) {

    }

    @Override
    protected void onBindVHoffset(VlayoutViewHolder viewHolder, int position, int offsetTotal) {
        //TODO: 写入数据到对应的布局文件中去
        viewHolder.getVBinding().setVariable(BR.studensinfo,datas.get(position));
        viewHolder.getVBinding().setVariable(BR.postion,position);
        viewHolder.getVBinding().setVariable(BR.adpter,this);
        viewHolder.getVBinding().executePendingBindings();
    }
    public void onItemClick(int position){
        Toast.makeText(mContext,"Press Position "+ (position+1),Toast.LENGTH_SHORT).show();
    }
}
