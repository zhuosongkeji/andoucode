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
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailCommentBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 11:35
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CommentAdapter extends BaseQuickAdapter<HotelDetailCommentBean, BaseViewHolder> {

    public CommentAdapter() {
        super(R.layout.comment_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelDetailCommentBean item) {

        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getAvator())).apply(new RequestOptions()
        .placeholder(R.mipmap.ic_placeholder)).into((ImageView) helper.getView(R.id.IvHeadPic));

        helper.setText(R.id.mTvName,item.getName())
                .setText(R.id.tv_time,item.getCreated_at())
                .setText(R.id.tv_des,item.getContent());
        ScaleRatingBar scaleRatingBar= helper.getView(R.id.simpleRatingBar);
        String stars = item.getStars();
        float parseFloat = Float.parseFloat(stars);
        scaleRatingBar.setRating(parseFloat);


    }
}
