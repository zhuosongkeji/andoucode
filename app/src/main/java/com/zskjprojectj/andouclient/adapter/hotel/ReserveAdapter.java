package com.zskjprojectj.andouclient.adapter.hotel;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
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
public class ReserveAdapter extends BaseQuickAdapter<HotelDetailReserveBean.HotelRoomBean, BaseViewHolder> {
    public ReserveAdapter() {
        super(R.layout.reserver_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelDetailReserveBean.HotelRoomBean item) {
        helper.addOnClickListener(R.id.online_reserver)
                .setText(R.id.hotel_name, item.getHouse_name())
                .setText(R.id.hotel_price, item.getPrice() + "")
                .setText(R.id.hotel_des,item.getName());
        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getImg())).apply(new RequestOptions()
        .placeholder(R.drawable.default_image).error(R.drawable.default_image))
                .into((ImageView) helper.getView(R.id.hotel_image));

        Log.d("wangbin", "convert: "+item.getId());
    }
}
