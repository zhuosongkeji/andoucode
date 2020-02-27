package com.zskjprojectj.andouclient.adapter.hotel;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/6 14:48
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelStarAdapter extends BaseQuickAdapter<HotelSearchConditionBean.StarBean, BaseViewHolder> {
    public static int SELECTOR_POSITION = -1;

    public HotelStarAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelSearchConditionBean.StarBean item) {
        helper.setText(R.id.mTvPrice, item.getName());
        TextView mPrice = helper.getView(R.id.mTvPrice);
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
