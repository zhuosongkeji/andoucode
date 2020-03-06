package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.BrowsingBean;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class HistoryListAdapter extends BaseAdapter<BrowsingBean> {
    public HistoryListAdapter() {
        super(R.layout.layout_history_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrowsingBean item) {
        helper.setText(R.id.busiess_name1_textview, item.getName())
                .setText(R.id.busiess_address1_textview, item.getAddress())
                .setText(R.id.busiess_tel_textview, item.getTel())
                .setText(R.id.busiess_dianzancount1_textview, item.getPraise_num())
                .setGone(R.id.stateImg, selectMode != SelectMode.NONE);
        helper.getView(R.id.stateImg).setSelected(isSelect(item));
        Glide.with(mContext)
                .load(UrlUtil.INSTANCE.getImageUrl(item.getLogo_img()))
                .into((ImageView) helper.getView(R.id.busiess_tupian1_image));
        ScaleRatingBar scaleRatingBar = helper.getView(R.id.simpleRatingBar);
        String stars_all = item.getStars_all();
        float starNum = Float.parseFloat(stars_all);
        scaleRatingBar.setRating(starNum);
    }
}

