package com.aorise.study.query;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aorise.study.R;
import com.aorise.study.base.LogT;
import com.aorise.study.base.MulityDataAdapter;
import com.aorise.study.base.MulityRecycler;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private List<MulityRecycler> DATAS = new ArrayList<>();
    private MulityDataAdapter mulityDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRecycler = (RecyclerView) findViewById(R.id.recycle);
        MulityRecycler mBase = new MulityRecycler(0, 0, "Title");
        List<MulityRecycler> datas = new ArrayList<>();
        List<MulityRecycler> senconddatas = new ArrayList<>();
        List<MulityRecycler> thriddatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            MulityRecycler data = new MulityRecycler(0, 0, "Title" + "." + 0 + "." + i);
            for (int m = 0; m < 10; m++) {

                MulityRecycler senconddata = new MulityRecycler(0, 0, "Title" + "." + 0 + "." + i + "." + m);
                for (int n = 0; n < 10; n++) {
                    LogT.d("i is " + i);
                    LogT.d("m is " + m);
                    LogT.d("n is " + n);
                    MulityRecycler thriddata = new MulityRecycler(0, 0, "Title" + "." + 0 + "." + i + "." + m + "." + n);
                    thriddatas.add(thriddata);
                }
                senconddata.setData(thriddatas);
                senconddatas.add(senconddata);
            }
            data.setData(senconddatas);
            datas.add(data);
        }
        mBase.setData(datas);

//        MulityRecycler mBase1 = new MulityRecycler(1, 0, "Title");
//        MulityRecycler mBase2 = new MulityRecycler(2, 0, "Title");
        DATAS.add(mBase);
//        DATAS.add(mBase1);
//        DATAS.add(mBase2);
        mulityDataAdapter = new MulityDataAdapter(DATAS, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mulityDataAdapter);
    }
}
