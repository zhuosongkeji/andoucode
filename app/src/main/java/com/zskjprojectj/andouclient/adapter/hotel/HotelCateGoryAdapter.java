package com.zskjprojectj.andouclient.adapter.hotel;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.hotel.HotelCategoryBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/2 17:53
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelCateGoryAdapter extends BaseQuickAdapter<HotelCategoryBean, BaseViewHolder> {


    public HotelCateGoryAdapter() {
        super(R.layout.hotel_category_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelCategoryBean item) {

        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getImg())).apply(new RequestOptions()
                .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) helper.getView(R.id.iv_hotel_category_img));
        helper.setText(R.id.tv_hotel_category_name,item.getName());

    }
}
