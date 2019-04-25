package com.aorise.study.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuliyuan.
 * Date: 2019/4/25.
 */
public class MulityRecycler {
    private int id ;
    private int parentId;
    private int position;

    private boolean isExpanded;
    private boolean isSelected;
    private String content;
    private ArrayList<MulityRecycler> data;

    public MulityRecycler(int id, int parentId, String content) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<MulityRecycler> getData() {
        return data;
    }

    public void setData(ArrayList<MulityRecycler>  data) {
        this.data = data;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
