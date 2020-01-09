package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;

import java.util.List;

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
    public HotelStarAdapter(int layoutResId, @Nullable List<HotelSearchConditionBean.StarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelSearchConditionBean.StarBean item) {
            helper.setText(R.id.tv_price,item.getName());
    }
}
