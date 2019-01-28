package com.aorise.study.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.aorise.study.R;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/28.
 */
public class SubAdapter extends DelegateAdapter.Adapter<SubAdapter.MainViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;


        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.text, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, final int position, int offsetTotal) {
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
            ((TextView) holder.itemView.findViewById(R.id.title)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext," Press Text " + (position+1),Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    public static class MainViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;
        private ViewDataBinding viewDataBinding;
        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }



        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
    }




