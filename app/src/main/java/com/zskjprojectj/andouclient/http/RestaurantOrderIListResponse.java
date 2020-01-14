package com.zskjprojectj.andouclient.http;

import com.zhuosongkj.android.library.model.IListData;
import com.zskjprojectj.andouclient.model.RestaurantOrder;

import java.util.ArrayList;

public class RestaurantOrderIListResponse extends ArrayList<RestaurantOrder> implements IListData<RestaurantOrder> {
    @Override
    public ArrayList<RestaurantOrder> getDataList() {
        return this;
    }
}
