package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.model.OrderStatus;

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
                .setText(R.id.statusTxt, getStatusStr(item.status))
                .setText(R.id.total, "￥" + item.pay_money)
                .addOnClickListener(R.id.btn_orderdetails)
                .addOnClickListener(R.id.btn_gotopayment)
                .addOnClickListener(R.id.btn_getgoods)
                .addOnClickListener(R.id.btn_check_logistics)
                .addOnClickListener(R.id.btn_comment);
        helper.setGone(R.id.btn_gotopayment, OrderStatus.DAI_FU_KUAN.status.equals(item.status));
        helper.setGone(R.id.btn_getgoods, OrderStatus.DAI_SHOU_HUO.status.equals(item.status));
        helper.setGone(R.id.btn_check_logistics,OrderStatus.DAI_SHOU_HUO.status.equals(item.status));
        helper.setGone(R.id.btn_comment,OrderStatus.DAI_PING_JIA.status.equals(item.status));
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.img)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(R.drawable.default_image))
                .into((ImageView) helper.getView(R.id.meshop_pic));
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.logo_img)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(R.drawable.default_image))
                .into((ImageView) helper.getView(R.id.shopIconImg));
    }

    private String getStatusStr(String status) {
        //10-未支付 20-已支付 40-已发货 50-交易成功（确认收货） 60-交易关闭（已评论）
        switch (status) {
            case "10":
                return "待付款";
            case "20":
                return "待发货";
            case "40":
                return "待收货";
            case "50":
                return "待评价";
            default:
                return "";
        }
    }
}
