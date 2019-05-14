package com.aorise.study.query;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.aorise.study.R;
import com.aorise.study.base.MulityDataAdapter;
import com.aorise.study.base.MulityRecycler;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private ArrayList<MulityRecycler> DATAS = new ArrayList<>();
    private MulityDataAdapter mulityDataAdapter;
    private int id = 10;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRecycler = (RecyclerView) findViewById(R.id.recycle);
        MulityRecycler mBase = new MulityRecycler(1, 0, "Title");
        ArrayList<MulityRecycler> datas = new ArrayList<>();
        ArrayList<MulityRecycler> data1 = new ArrayList<>();
        ArrayList<MulityRecycler> data2 = new ArrayList<>();

        for (int i = 2; i < 6; i++) {
            MulityRecycler mulityRecycler = new MulityRecycler(i, 1, "Content " + 1 + "i." + i);
            for (int m = 1; m < 5; m++) {
                MulityRecycler mulityRecycler1 = new MulityRecycler(m + mulityRecycler.getId(), mulityRecycler.getId(), "Content " + 1 + "i." + i + "m." + m);
                mulityRecycler1.setParentData(mulityRecycler);
                for (int n = 1; n < 5; n++) {
                    MulityRecycler mulityRecycler2 = new MulityRecycler(n + mulityRecycler1.getId(), mulityRecycler1.getId(), "Content " + 1 + "i." + i + "m." + m + "n." + n);
                    mulityRecycler2.setParentData(mulityRecycler1);
                    data2.add(mulityRecycler2);
                }
                mulityRecycler1.setData(data2);
                data1.add(mulityRecycler1);

            }
            mulityRecycler.setData(data1);
            datas.add(mulityRecycler);

        }

        mBase.setData(datas);
//        initData(new MulityRecycler(id,16,"分类"+(id -1)+"_"+(id -1)));

        DATAS.add(mBase);

        mulityDataAdapter = new MulityDataAdapter(DATAS, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(manager);
        mRecycler.setAdapter(mulityDataAdapter);
    }

}
