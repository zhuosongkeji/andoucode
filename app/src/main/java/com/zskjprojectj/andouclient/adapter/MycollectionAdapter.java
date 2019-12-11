package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.MycollectionBean;

import java.util.List;

public class MycollectionAdapter extends BaseQuickAdapter<MycollectionBean, BaseViewHolder> {
    public MycollectionAdapter(int layoutResId, @Nullable List<MycollectionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MycollectionBean item) {

    }
}
