package com.aorise.study.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aorise.study.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tuliyuan.
 * Date: 2019/4/25.
 */
public class MulityDataAdapter extends RecyclerView.Adapter<MulityDataAdapter.BaseViewHolder> {
    List<MulityRecycler> alldatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater layoutInflater;
    private MulityDataItemClick itemClick;
    private MulityDataAdapter adapter;

    public MulityDataAdapter(final List<MulityRecycler> datas, Context context) {
        alldatas = datas;
        adapter = this;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemClick = new MulityDataItemClick() {
            @Override
            public void itemClick(int position) {
                LogT.d(" position content is " + alldatas.get(position).getContent() + " position is "+ position);
                if (alldatas.get(position).isExpanded()) {

                    MulityRecyclerHelper.FlodList(alldatas.get(position), adapter);
                } else {
                    alldatas.get(position).setExpanded(true);
                    MulityRecyclerHelper.ExpandedList(alldatas.get(position), adapter,position);
                }
            }
        };
    }

    @NonNull
    @Override
    public MulityDataAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.mulitylist_item, viewGroup, false);
        return new MulityDataAdapter.BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MulityDataAdapter.BaseViewHolder viewHolder, final int position) {
        TextView title = (TextView) viewHolder.itemView.findViewById(R.id.mulity_title);
        TextView content = (TextView) viewHolder.itemView.findViewById(R.id.mulity_content);
        title.setText(alldatas.get(position).getParentId() + "." + alldatas.get(position).getId());
        content.setText(alldatas.get(position).getContent());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alldatas.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void ExpandList(List<MulityRecycler> datas, int position) {
       // this.alldatas.addAll(position, datas);
        if(position == alldatas.size()-1){
            this.alldatas.addAll(datas);
        }else{
            this.alldatas.addAll(position + 1, datas);
        }
        notifyDataSetChanged();
    }

    public void FlodList(List<MulityRecycler> datas) {
        for (MulityRecycler data : datas) {
            if (this.alldatas.contains(data)) {
                this.alldatas.remove(data);
            }
        }
        notifyDataSetChanged();
    }

    interface MulityDataItemClick {
        void itemClick(int position);
    }
}
