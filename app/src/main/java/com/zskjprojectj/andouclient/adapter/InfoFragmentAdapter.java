package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.InfoFragmentBean;

import java.util.List;

public class InfoFragmentAdapter extends BaseQuickAdapter<InfoFragmentBean, BaseViewHolder> {
    public InfoFragmentAdapter(int layoutResId, @Nullable List<InfoFragmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoFragmentBean item) {

    }
}
