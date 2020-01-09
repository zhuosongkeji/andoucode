package com.zskjprojectj.andouclient.adapter.restaurant;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.RestaurantCategory;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class RestaurantCategoryAdapter extends BaseAdapter<RestaurantCategory> {
    public RestaurantCategoryAdapter() {
        super(R.layout.layout_restaurant_catgory_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RestaurantCategory item) {
        helper.setText(R.id.nameTxt, item.name);
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.getImageUrl(item.img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) helper.itemView.findViewById(R.id.iconImg));
    }
}