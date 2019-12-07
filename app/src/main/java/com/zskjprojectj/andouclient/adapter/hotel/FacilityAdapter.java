package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailFacilityBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 14:00
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class FacilityAdapter extends BaseQuickAdapter<HotelDetailFacilityBean, BaseViewHolder> {
    public FacilityAdapter(int layoutResId, @Nullable List<HotelDetailFacilityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelDetailFacilityBean item) {

    }
}
