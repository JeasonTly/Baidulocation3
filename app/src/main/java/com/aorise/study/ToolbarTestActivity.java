package com.aorise.study;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aorise.study.databinding.ActivityToolbarBinding;

/**
 * PROJECT_NAME:Baidulocation.
 * Created by Tuliyuan.
 * Date: 2019/1/23.
 */
public class ToolbarTestActivity extends AppCompatActivity {
    ActivityToolbarBinding dataBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_toolbar);
        setSupportActionBar(dataBinding.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
