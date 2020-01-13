package com.zskjprojectj.andouclient.model;

import com.zhuosongkj.android.library.model.ListData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListResponse extends ArrayList<Restaurant> implements ListData<Restaurant> {
    @Override
    public ArrayList<Restaurant> getDataList() {
        return this;
    }
}
