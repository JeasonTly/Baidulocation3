package com.aorise.study.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class VlayoutViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private VB vb;
    public VlayoutViewHolder(VB vb ) {
        super(vb.getRoot());
        this.vb = vb;
    }
    public VB getVBinding(){
        if(vb ==null){
            System.out.println("Aorise Error: VlayoutViewHolder VB NOT INIT");
            return  null;
        }else{
            return vb;
        }
    }
}
