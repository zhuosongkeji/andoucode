package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.MehotelorderBean;

import java.util.List;

public class MehotelorderAdapter extends BaseQuickAdapter<MehotelorderBean, BaseViewHolder> {
    public MehotelorderAdapter(int layoutResId, @Nullable List<MehotelorderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MehotelorderBean item) {

    }
}
