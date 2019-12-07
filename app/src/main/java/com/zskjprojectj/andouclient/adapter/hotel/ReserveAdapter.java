package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;

import java.util.HashSet;
import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 10:30
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ReserveAdapter extends BaseQuickAdapter<HotelDetailReserveBean, BaseViewHolder> {
    public ReserveAdapter(int layoutResId, @Nullable List<HotelDetailReserveBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelDetailReserveBean item) {
        helper.addOnClickListener(R.id.online_reserver);
    }
}
