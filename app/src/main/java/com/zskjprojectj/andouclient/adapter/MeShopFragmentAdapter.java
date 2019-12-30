package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.MeShopFragmentBean;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.utils.GlideTool;

import java.util.List;

/**
 * 个人中心商城订单全部订单适配器对象
 */
public class MeShopFragmentAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {
    public MeShopFragmentAdapter() {
        super(R.layout.item_meshop);
    }

    @Override
    protected void convert(BaseViewHolder helper, Order item) {
        helper.setText(R.id.shopNameTxt, item.mname)
                .setText(R.id.orderNumTxt, "订单编号:" + item.order_id)
                .setText(R.id.titleTxt, item.name)
                .setText(R.id.priceTxt, "￥" + item.price)
                .setText(R.id.countTxt, "x " + item.num)
                .setText(R.id.statusTxt, item.status)
                .setText(R.id.total, "￥" + item.pay_money)
                .addOnClickListener(R.id.btn_orderdetails);
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.img)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(R.drawable.default_image))
                .into((ImageView) helper.getView(R.id.meshop_pic));
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.logo_img)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(R.drawable.default_image))
                .into((ImageView) helper.getView(R.id.shopIconImg));
    }
}
