package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.PlatformshoppingcartBean;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.GlideTool;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.util.List;

public class PlatformshoppingcartAdapter extends BaseQuickAdapter<CartItem, BaseViewHolder> {
    public PlatformshoppingcartAdapter() {
        super(R.layout.fragment_mall_shopping_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartItem item) {
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.getImageUrl(item.logo_img))
                .into((ImageView) helper.itemView.findViewById(R.id.shopIconImg));
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.getImageUrl(item.img))
                .into((ImageView) helper.itemView.findViewById(R.id.img_cartgoodspic));
        helper.setText(R.id.shopNameTxt, item.merchant_name)
                .setText(R.id.tv_cartgoodsname, item.goods_name)
                .setText(R.id.tv_cartgoodprice, "￥" + item.price)
                .setText(R.id.tv_num, item.num + "")
                .addOnClickListener(R.id.deleteBtn);
    }
}
