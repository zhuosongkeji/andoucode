package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.SquareBean;

import java.util.List;

public class SquareAdapter extends BaseQuickAdapter<SquareBean, BaseViewHolder> {
    public SquareAdapter(int layoutResId, @Nullable List<SquareBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareBean item) {

    }
}
