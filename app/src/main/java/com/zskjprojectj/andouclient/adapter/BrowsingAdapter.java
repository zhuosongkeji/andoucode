package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.BrowsingBean;

import java.util.List;

/**
 * 浏览痕迹适配器
 */
public class BrowsingAdapter extends BaseQuickAdapter<BrowsingBean, BaseViewHolder> {
    public BrowsingAdapter() {
        super(R.layout.item_browsing);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrowsingBean item) {
        helper.setText(R.id.busiess_name1_textview, item.getName()).setText(R.id.busiess_address1_textview, item.getAddress()).setText(R.id.tv_browsingnum, item.getTel());
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.getLogo_img()).into((ImageView) helper.getView(R.id.busiess_tupian1_image));
        ScaleRatingBar scaleRatingBar=  helper.getView(R.id.simpleRatingBar);
        String stars_all = item.getStars_all();
        float starNum = Float.parseFloat(stars_all);
        scaleRatingBar.setRating(starNum);
    }
}
