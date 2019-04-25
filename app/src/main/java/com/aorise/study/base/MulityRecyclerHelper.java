package com.aorise.study.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/4/25.
 */
public class MulityRecyclerHelper {
    private static List<MulityRecycler> helpers = new ArrayList<>();

    public static void ExpandedList(MulityRecycler data, MulityDataAdapter adapter, int position) {
        if (data.getData() != null && data.getData().size() != 0) {
            helpers.clear();
            adapter.ExpandList(data.getData(), position);
        }
    }

    public static void FlodList(MulityRecycler data, MulityDataAdapter adapter) {
        if (data.getData() != null && data.getData().size() != 0) {
            FlodAll(data.getData());
            adapter.FlodList(helpers);
            data.setExpanded(false);
        }
    }

    private static void FlodAll(List<MulityRecycler> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isExpanded()) {
                FlodAll(data.get(i).getData());
            }
            helpers.add(data.get(i));
        }
    }
}
