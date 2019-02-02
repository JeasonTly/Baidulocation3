package com.aorise.study.query;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aorise.study.R;
import com.aorise.study.databinding.ActivityQueryBinding;
import com.aorise.study.query.fragment.JobHolderFragment;
import com.aorise.study.query.fragment.LawsFragment;
import com.aorise.study.query.fragment.NewsFragment;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/31.
 */
public class QueryActivity extends AppCompatActivity {
    ActivityQueryBinding activityQueryBinding;
    private Intent mIntent ;
    private Fragment CurrentFragment;
    private static String IntentFragmentTag ;
    private static String ToolBarTitle ;
    //private Fragment NewsFragment,JobHolderFragment,LawsFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQueryBinding = DataBindingUtil.setContentView(this, R.layout.activity_query);

        mIntent = getIntent();
        if(mIntent != null && mIntent.hasExtra("intent_fragment")){
            IntentFragmentTag = mIntent.getStringExtra("intent_fragment");
            // laws 为法律法规界面，news为新闻界面 jobholder 为行业人员界面
            replaceFragment(IntentFragmentTag);
        }
    }

    private void replaceFragment(String tag) {
        if (CurrentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(CurrentFragment).commit();
        }
        CurrentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (CurrentFragment == null) {
            switch (tag) {
                case "laws":
                    CurrentFragment = new LawsFragment();
                    ToolBarTitle = "laws";
                    break;
                case "news":
                    CurrentFragment = new NewsFragment();
                    ToolBarTitle = "news";
                    break;
                case "jobholder":
                    CurrentFragment = new JobHolderFragment();
                    ToolBarTitle = "jobholder";
                    break;
                case "":
                    CurrentFragment = new NewsFragment();
                    ToolBarTitle = "news";
                    break;
            }

            getSupportFragmentManager().beginTransaction().add(R.id.query_framelayout, CurrentFragment, tag).commit();
        }else {
            getSupportFragmentManager().beginTransaction().show(CurrentFragment).commit();
        }

        activityQueryBinding.toolbarTitle.setText(ToolBarTitle);
        activityQueryBinding.queryToolbar.setNavigationIcon(R.drawable.icon_back);
        activityQueryBinding.queryToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryActivity.this.finish();
            }
        });

        setSupportActionBar(activityQueryBinding.queryToolbar);
        //activityQueryBinding.queryToolbar.setTitle(ToolBarTitle);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
