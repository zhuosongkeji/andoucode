package com.zskjprojectj.andouclient.model;

import com.zhuosongkj.android.library.model.IListData;

import java.util.ArrayList;

public class MiaoShaListResponse implements IListData<MiaoShaGoods> {
    public int sec_status;

    public ArrayList<MiaoShaGoods> top_goods = new ArrayList<>();
    public ArrayList<MiaoShaGoods> goods_list = new ArrayList<>();

    @Override
    public ArrayList<MiaoShaGoods> getDataList() {
        return goods_list;
    }
}
