package com.aorise.study.contact;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.aorise.study.R;
import com.aorise.study.base.LogT;

/**
 * Created by Tuliyuan.
 * Date: 2019/2/13.
 */
public class  ContactsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cotacts_main);

        CrumbView crumbView = (CrumbView) findViewById(R.id.crumb_view);
        crumbView.setActivity(ContactsActivity.this);

        int firstLevel = 1;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setBreadCrumbTitle(getString(R.string.crumb_title, firstLevel));
        ft.replace(R.id.frag_container, Myfragment.getInstance(firstLevel));
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        LogT.d(" getSupportFragmentManager().getBackStackEntryCount()  "+getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            this.finish();
        }else{
            super.onBackPressed();
        }
    }
}
