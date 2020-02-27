package com.zskjprojectj.andouclient.adapter.hotel;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
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
    public HotelHomeAdapter() {
        super(R.layout.activity_hotel_home_view);
    }


    @Override
    protected void convert(BaseViewHolder helper, HotelHomeBean item) {

        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getLogo_img())).apply(new RequestOptions()
        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) helper.getView(R.id.busiess_tupian1_image));

        helper.setText(R.id.busiess_name1_textview,item.getName());

        //设置星级
        String stars_all = item.getStars_all();
        float aFloat = Float.parseFloat(stars_all);
        ScaleRatingBar scaleRatingBar = helper.getView(R.id.simpleRatingBar);
        scaleRatingBar.setRating(aFloat);

        helper.setText(R.id.busiess_address1_textview,item.getAddress())
                .setText(R.id.busiess_dianzancount1_textview,item.getPraise_num())
                .setText(R.id.tv_hotel_price,item.getPrice());



    }
}
