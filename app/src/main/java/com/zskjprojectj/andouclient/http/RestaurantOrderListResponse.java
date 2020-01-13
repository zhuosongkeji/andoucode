package com.zskjprojectj.andouclient.http;

import com.zhuosongkj.android.library.model.ListData;
import com.zskjprojectj.andouclient.model.RestaurantOrder;

import java.util.ArrayList;

public class RestaurantOrderListResponse extends ArrayList<RestaurantOrder> implements ListData<RestaurantOrder> {
    @Override
    public ArrayList<RestaurantOrder> getDataList() {
        return this;
    }
}
