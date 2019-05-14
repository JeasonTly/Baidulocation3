package com.aorise.study;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aorise.study.adapter.BaseViewHolder;
import com.aorise.study.base.LogT;
import com.aorise.study.bean.WinningData;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<WinningData> dataList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //  super.handleMessage(msg);
            Message message = new Message();
            message.what = msg.what + 1;
            mHandler.sendMessageDelayed(message, 1000);
            if (msg.what == dataList.size() - 1) {
                message.what = 0;
                recyclerView.scrollToPosition(0);
            } else {
                recyclerView.smoothScrollToPosition(message.what);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        for (int i = 0; i < 6; i++) {
            dataList.add(new WinningData("Name__" + i, "免费奖品XXX" + i));
        }
        recyclerView = (RecyclerView) findViewById(R.id.winning_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(Main3Activity.this).inflate(R.layout.winning_item, null);
                return new WinViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.winning_item_text);
                textView.setText(getString(R.string.winning_info, dataList.get(i).getName(), dataList.get(i).getContent()));
                LogT.d("viewHolder.itemView  " + viewHolder.itemView.getMeasuredHeight());
                LogT.d("textView " + textView.getMeasuredHeight());
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        });
        Message msg = new Message();
        msg.what = 0;
        mHandler.sendMessageDelayed(msg, 1000);
    }

    class WinViewHolder extends RecyclerView.ViewHolder {
        public WinViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
