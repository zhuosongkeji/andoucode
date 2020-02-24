package com.zskjprojectj.andouclient.adapter.hotel;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;

import java.util.List;

public class HotelPriceAdapter extends BaseQuickAdapter<HotelSearchConditionBean.PriceRangeBean, BaseViewHolder> {

    public static int SELECTOR_POSITION = -1;

    public HotelPriceAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, HotelSearchConditionBean.PriceRangeBean item) {
        String price = item.getStart() + "~" + item.getEnd();
        helper.setText(R.id.tv_price, price);
        TextView mPrice = helper.getView(R.id.tv_price);
        int p = helper.getLayoutPosition();
        if (SELECTOR_POSITION == p) {
            mPrice.setSelected(true);
        } else {
            mPrice.setSelected(false);
        }
    }

    public void onChange(int position) {
        SELECTOR_POSITION = position;
        notifyDataSetChanged();
    }


    public void cancel(int position) {
        SELECTOR_POSITION = position;
        notifyDataSetChanged();
    }
}
