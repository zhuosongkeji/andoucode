package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class CategoryLevelAdapter extends BaseAdapter<MallGoodsCateBean> {

    public CategoryLevelAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallGoodsCateBean item) {
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.INSTANCE.getImageUrl(item.img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) helper.itemView.findViewById(R.id.img));
        helper.setText(R.id.titleTxt, item.name);
    }
}
