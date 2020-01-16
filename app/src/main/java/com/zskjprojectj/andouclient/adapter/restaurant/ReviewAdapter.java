package com.zskjprojectj.andouclient.adapter.restaurant;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Review;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class ReviewAdapter extends BaseAdapter<Review> {
    public ReviewAdapter() {
        super(R.layout.layout_restaurant_review_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Review item) {
        helper.setText(R.id.nameTxt, item.name)
                .setText(R.id.dateTxt, item.created_at)
                .setText(R.id.contentTxt, item.content);
        ((ScaleRatingBar) helper.itemView.findViewById(R.id.ratingBar)).setRating(item.stars);
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.getImageUrl(item.avator))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) helper.itemView.findViewById(R.id.avatarImg));
    }
}
