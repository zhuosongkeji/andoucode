package com.zskjprojectj.andouclient.adapter.hotel;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;
import com.zskjprojectj.andouclient.model.Goods;
import com.zskjprojectj.andouclient.utils.UrlUtil;

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
public class ReserveAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public ReserveAdapter(int layoutResId, @Nullable List<Goods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.addOnClickListener(R.id.online_reserver)
                .setText(R.id.hotel_name, item.name)
                .setText(R.id.hotel_price, item.price + "");
        Glide.with(helper.itemView).load(UrlUtil.getImageUrl(item.img))
                .into((ImageView) helper.itemView.findViewById(R.id.hotel_image));
    }
}
