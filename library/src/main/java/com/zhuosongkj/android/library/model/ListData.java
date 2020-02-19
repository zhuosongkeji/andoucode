package com.zhuosongkj.android.library.model;

import java.util.ArrayList;

public class ListData<T> extends ArrayList<T> implements IListData<T> {
    @Override
    public ArrayList<T> getDataList() {
        return this;
    }
}
