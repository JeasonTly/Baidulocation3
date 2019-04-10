package com.aorise.study.contact;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aorise.study.R;

import java.util.ArrayList;

/**
 * Created by Tuliyuan.
 * Date: 2019/2/13.
 */

public class Myfragment extends ListFragment {
    private static final String KEY_LEVEL = "level";
    private FragmentManager mFragmentManager;
    private int mLevel;

    public static Myfragment getInstance(int level){
        Myfragment frag = new Myfragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LEVEL, level);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getActivity().getSupportFragmentManager();
        mLevel = 1;
        if(getArguments() != null){
            mLevel = getArguments().getInt(KEY_LEVEL, 1);
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            arrayList.add(getString(R.string.list_item_txt, mLevel, i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setBreadCrumbTitle(getString(R.string.crumb_title, mLevel + 1));
        ft.replace(R.id.frag_container, Myfragment.getInstance(mLevel + 1));
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
}
