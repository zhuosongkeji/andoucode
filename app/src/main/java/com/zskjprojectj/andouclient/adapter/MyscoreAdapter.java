package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.MyscoreBean;

import java.util.List;

public class MyscoreAdapter extends BaseQuickAdapter<MyscoreBean, BaseViewHolder> {
    public MyscoreAdapter(int layoutResId, @Nullable List<MyscoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyscoreBean item) {

    }
}
