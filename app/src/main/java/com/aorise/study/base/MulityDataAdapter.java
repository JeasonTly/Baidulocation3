package com.aorise.study.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aorise.study.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tuliyuan.
 * Date: 2019/4/25.
 */
public class MulityDataAdapter extends RecyclerView.Adapter<MulityDataAdapter.BaseViewHolder> {
    ArrayList<MulityRecycler> alldatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater layoutInflater;
    private MulityDataItemClick itemClick;
    private MulityDataAdapter adapter;

    public MulityDataAdapter(final ArrayList<MulityRecycler> datas, final Context context) {
        alldatas = datas;
        adapter = this;
        mContext = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemClick = new MulityDataItemClick() {
            @Override
            public void itemClick(int position) {

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
        title.setText("            ");
        content.setText(alldatas.get(position).getContent());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogT.d(" position content is " + alldatas.get(position) + " position is "
                        + position + "");
                if (alldatas.get(position).getData() == null || alldatas.get(position).getData().size() == 0) {
                    LogT.d("show Tost");
                    Toast.makeText(mContext, alldatas.get(position).getContent(), Toast.LENGTH_SHORT).show();
                }
                if (alldatas.get(position).isExpanded()) {
                    if (alldatas.get(position).getData() != null && alldatas.get(position).getData().size() != 0) {
                        MulityRecyclerHelper.FlodList(alldatas.get(position), adapter, position);
                    }
                } else {
                    MulityRecyclerHelper.ExpandedList(alldatas.get(position), adapter, position);
                }
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
        if (position == alldatas.size() - 1) {
            alldatas.addAll(datas);
        } else {
            alldatas.addAll(position + 1, datas);
        }
        notifyDataSetChanged();
    }

    public void FlodList(ArrayList<MulityRecycler> datas, int position) {
        LogT.d("datas size is " + datas.size() + " position is " + position + " alldatas size is  " + alldatas.size());
        for (MulityRecycler d : alldatas) {
            LogT.d(" d is " + d.getContent());
        }
        alldatas.subList(position + 1, position + 1 + datas.size()).clear();
        LogT.d("alldatas size is  " + alldatas.size());
        notifyDataSetChanged();
    }

    interface MulityDataItemClick {
        void itemClick(int position);
    }
}
