package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.hotel.HotelResultBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/6 15:29
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelResultAdapter  extends BaseQuickAdapter<HotelResultBean,BaseViewHolder>{

    public HotelResultAdapter(int layoutResId, @Nullable List<HotelResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelResultBean item) {

    }
}
