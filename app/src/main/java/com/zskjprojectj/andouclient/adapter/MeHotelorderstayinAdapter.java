package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MeHotelorderstayinBean;

import java.util.List;

public class MeHotelorderstayinAdapter extends BaseQuickAdapter<MeHotelorderstayinBean, BaseViewHolder> {
    public MeHotelorderstayinAdapter(int layoutResId, @Nullable List<MeHotelorderstayinBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeHotelorderstayinBean item) {
        helper.addOnClickListener(R.id.btn_hotelorderstayindetails);
    }
}
