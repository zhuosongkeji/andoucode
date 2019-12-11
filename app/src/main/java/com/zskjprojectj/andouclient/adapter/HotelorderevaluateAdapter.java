package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.HotelorderevaluateBean;

import java.util.List;

public class HotelorderevaluateAdapter extends BaseQuickAdapter<HotelorderevaluateBean, BaseViewHolder> {
    public HotelorderevaluateAdapter(int layoutResId, @Nullable List<HotelorderevaluateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelorderevaluateBean item) {

    }
}
