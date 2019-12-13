package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/12 9:37
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelHomeAdapter extends BaseQuickAdapter<HotelHomeBean, BaseViewHolder> {
    public HotelHomeAdapter(int layoutResId, @Nullable List<HotelHomeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelHomeBean item) {

    }
}
