package com.zskjprojectj.andouclient.entity;

import android.view.View;
import android.widget.ImageView;

import com.wihaohao.PageGridView;

public class BookorderBean implements PageGridView.ItemModel{
    private String name;


    private int iconId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public BookorderBean(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public void setIcon(ImageView imageView) {
        imageView.setImageResource(iconId);
    }

    @Override
    public void setItemView(View itemView) {

    }
}
