package com.zskjprojectj.andouclient.model;

import com.zhuosongkj.android.library.model.IListData;

import java.util.ArrayList;

public class RestaurantIListResponse extends ArrayList<Restaurant> implements IListData<Restaurant> {
    @Override
    public ArrayList<Restaurant> getDataList() {
        return this;
    }
}
