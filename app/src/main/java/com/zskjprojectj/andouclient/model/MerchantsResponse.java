package com.zskjprojectj.andouclient.model;

import com.zhuosongkj.android.library.model.IListData;

import java.util.ArrayList;

public class MerchantsResponse implements IListData<Merchant> {

    @Override
    public ArrayList<Merchant> getDataList() {
        return merchants;
    }

    public ArrayList<Merchant> merchants = new ArrayList<>();
}
