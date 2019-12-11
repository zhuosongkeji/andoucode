package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.MyaddressBean;

import java.util.List;

public class MyaddressAdapter extends BaseQuickAdapter<MyaddressBean, BaseViewHolder> {
    public MyaddressAdapter(int layoutResId, @Nullable List<MyaddressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyaddressBean item) {

    }
}
