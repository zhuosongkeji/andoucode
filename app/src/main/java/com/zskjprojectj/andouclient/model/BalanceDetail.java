package com.zskjprojectj.andouclient.model;

import com.zhuosongkj.android.library.model.IListData;

import java.util.ArrayList;

public class BalanceDetail implements IListData<BalanceLog> {
    public ArrayList<BalanceLog> log = new ArrayList<>();
    public String money;

    @Override
    public ArrayList<BalanceLog> getDataList() {
        return log;
    }
}
