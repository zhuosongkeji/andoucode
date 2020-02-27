package com.zskjprojectj.andouclient.adapter.hotel;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailFacilityBean;
import com.zskjprojectj.andouclient.utils.UrlUtil;

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
public class FacilityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FacilityAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item)).into((ImageView) helper.getView(R.id.imgbgall));
    }
}
