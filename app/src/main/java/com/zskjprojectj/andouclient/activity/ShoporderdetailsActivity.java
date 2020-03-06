package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.model.OrderDetail;
import com.zskjprojectj.andouclient.model.OrderStatus;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.IOException;

import static com.zskjprojectj.andouclient.utils.ConstantKt.KEY_DATA;

public class ShoporderdetailsActivity extends BaseActivity {

    private Button btn_gotopaymentdetail;
    private String goodsPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "商城订单");
        btn_gotopaymentdetail = findViewById(R.id.btn_gotopaymentdetail);
        btn_gotopaymentdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, MallOnlineOrderActivity.class);
            }
        });

        Order order = (Order) getIntent().getSerializableExtra(KEY_DATA);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getOrderDetail(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        order.order_id,
                        order.id
                ),
                result -> {
                    bindView(result.data);
                });
    }


    private void bindView(OrderDetail order) {
        if (OrderStatus.DAI_FU_KUAN.status.equals(order.status)) {
            findViewById(R.id.daifukuanContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_gotopaymentdetail).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_gotopaymentdetail).setOnClickListener(v ->
                    MallOnlineOrderActivity.start(order.order_sn, "", "", ""));
            findViewById(R.id.btn_refund).setVisibility(View.GONE);
        } else if (OrderStatus.DAI_FA_HUO.status.equals(order.status)) {
            findViewById(R.id.daifahuoContainer).setVisibility(View.VISIBLE);
        } else if (OrderStatus.DAI_SHOU_HUO.status.equals(order.status)) {
            findViewById(R.id.daishouhuoContainer).setVisibility(View.VISIBLE);

        } else if (OrderStatus.DAI_PING_JIA.status.equals(order.status)) {
            findViewById(R.id.daishouhuoContainer).setVisibility(View.VISIBLE);
        }
        ((TextView) findViewById(R.id.orderNumTxt)).setText("订单编号：" + order.order_sn);
        if (!TextUtils.isEmpty(order.pay_time)) {
            findViewById(R.id.dateTxt).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.dateTxt)).setText("支付时间：" + order.pay_time);
        }
        ((TextView) findViewById(R.id.receiverName)).setText("收件人：" + order.userinfo.name);
        ((TextView) findViewById(R.id.addressTxt)).setText("收货地址：" +
                order.userinfo.province +
                order.userinfo.city +
                order.userinfo.area +
                order.userinfo.address);
        ((TextView) findViewById(R.id.mobileTxt)).setText(order.userinfo.mobile);
        for (OrderDetail.Goodsdetail goods : order.details) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_goods_item, null);
            ((TextView) view.findViewById(R.id.titleTxt)).setText(goods.name);
            ((TextView) view.findViewById(R.id.specTxt)).setText(getSpec(goods.attr_value));
            ((TextView) view.findViewById(R.id.countTxt)).setText(goods.num);
            goodsPrice = goods.price;
            ((TextView) view.findViewById(R.id.priceTxt)).setText(goods.price);
            Glide.with(mActivity).load(UrlUtil.INSTANCE.getImageUrl(goods.img))
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                    .into((ImageView) view.findViewById(R.id.meshop_pic));
            ((ViewGroup) findViewById(R.id.goodsContainer)).addView(view);
        }
        ((TextView) findViewById(R.id.totalTxt)).setText("¥" + goodsPrice);
        ((TextView) findViewById(R.id.totalCountTxt)).setText("共" + order.allnum + "个商品(合计)");
        ((TextView) findViewById(R.id.freightTxt)).setText("+¥" + order.shipping_free);
        ((TextView) findViewById(R.id.costTxt)).setText("¥" + order.pay_money);


        /**
         * 退货退款
         */
        if (OrderStatus.DAI_FA_HUO.status.equals(order.status)) {
            findViewById(R.id.btn_refund).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopordersendetailsrefundActivity.start("refund", order);
                }
            });

        } else {

            findViewById(R.id.btn_refund).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopordersendetailsrefundActivity.start("sales_return", order);
                }
            });

        }
    }

    private String getSpec(String[] attr_value) {
        StringBuilder builder = new StringBuilder();
        for (String s : attr_value) {
            builder.append(s).append("+");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public static void start(Order order) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, order);
        ActivityUtils.startActivity(bundle, ShoporderdetailsActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_shoporderdetails;
    }
}
