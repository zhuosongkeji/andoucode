package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsListBean;

import java.util.List;

public class MallGoodsListAdapter extends BaseQuickAdapter<MallGoodsListBean, BaseViewHolder> {
    public MallGoodsListAdapter() {
        super(R.layout.item_classificationofgoods);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallGoodsListBean item) {

        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getImg())).apply(new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)).into((ImageView) helper.getView(R.id.iv_mall_list_img));

        helper.setText(R.id.tv_mall_list_name,item.getName());
        helper.setText(R.id.tv_mall_list_price,"¥"+item.getPrice());

    }

}
