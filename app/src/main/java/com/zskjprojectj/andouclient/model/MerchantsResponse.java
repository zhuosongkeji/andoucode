package com.zskjprojectj.andouclient.model;

import com.zhuosongkj.android.library.model.ListData;

import java.util.ArrayList;

public class MerchantsResponse implements ListData<Merchant> {

    @Override
    public ArrayList<Merchant> getDataList() {
        return merchants;
    }

    public ArrayList<Merchant> merchants = new ArrayList<>();
}
