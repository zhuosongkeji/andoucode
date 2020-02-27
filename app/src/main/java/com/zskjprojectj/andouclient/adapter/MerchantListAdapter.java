package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Merchant;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class MerchantListAdapter extends BaseQuickAdapter<Merchant, BaseViewHolder> {
    public MerchantListAdapter() {
        super(R.layout.merchant_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, Merchant item) {
        Glide.with(helper.itemView)
                .load(UrlUtil.INSTANCE.getImageUrl(item.logo_img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) helper.itemView.findViewById(R.id.busiess_tupian1_image));
        helper.setText(R.id.busiess_name1_textview, item.name)
                .setText(R.id.busiess_dianzancount1_textview, item.praise_num + "")
                .setText(R.id.busiess_address1_textview, item.address)
                .setText(R.id.mTvPrice, item.price);

        String stars_all = item.stars_all;
        float aFloat = Float.parseFloat(stars_all);
        ScaleRatingBar ratingBar = helper.getView(R.id.simpleRatingBar);
        ratingBar.setRating(aFloat);

    }
}
