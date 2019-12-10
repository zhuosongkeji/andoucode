package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;

import java.util.List;

public class BalancesubsidiaryAdapter extends BaseQuickAdapter <BalancesubsidiaryBean, BaseViewHolder> {

    public BalancesubsidiaryAdapter(int layoutResId, @Nullable List<BalancesubsidiaryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalancesubsidiaryBean item) {
        helper.setText(R.id.tv_title,item.getTitlename());
        helper.setText(R.id.tv_price,item.getPrice());
        helper.setText(R.id.tv_time,item.getTime());
    }
}
