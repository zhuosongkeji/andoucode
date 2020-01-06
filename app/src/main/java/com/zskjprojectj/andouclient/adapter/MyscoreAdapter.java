package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MyscoreBean;
import com.zskjprojectj.andouclient.model.IntegralDetail;
import com.zskjprojectj.andouclient.model.IntegralLog;

import java.util.List;

public class MyscoreAdapter extends BaseQuickAdapter<IntegralLog, BaseViewHolder> {
    public MyscoreAdapter() {
        super(R.layout.item_myscore);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralLog item) {
        helper.setText(R.id.tv_order_describe,item.describe).setText(R.id.tv_order_createtime,item.create_time);
        if (item.state==1)
        {
            helper.setText(R.id.tv_price, "+"+item.price);
        }else
        {
            helper.setText(R.id.tv_price, "-"+item.price);
        }
    }
}
