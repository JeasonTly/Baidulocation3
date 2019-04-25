package com.aorise.study.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/4/25.
 */
public class MulityRecyclerHelper {
    private static ArrayList<MulityRecycler> helpers = new ArrayList<>();

    public static void ExpandedList(MulityRecycler data, MulityDataAdapter adapter, int position) {
        if (data.getData() != null && data.getData().size() != 0) {
            helpers.clear();
            adapter.ExpandList(data.getData(), position);
            data.setExpanded(true);
        }
    }

    public static void FlodList(MulityRecycler data, MulityDataAdapter adapter,int position) {
        helpers.clear();
        if (data.getData() != null && data.getData().size() != 0) {
            FlodAll(data.getData());
            adapter.FlodList(helpers,position);
            data.setExpanded(false);
        }
    }

    private static void FlodAll(ArrayList<MulityRecycler> data) {
        LogT.d(" data size is " + data.size());
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getData() != null && data.get(i).getData().size() != 0 && data.get(i).isExpanded()) {
                data.get(i).setExpanded(false);//折叠所有已经打开的数据集合
                FlodAll(data.get(i).getData());//递归查询下面那些已经打开了的数据，并将之收起
            }
            helpers.add(data.get(i));
        }
    }
}
