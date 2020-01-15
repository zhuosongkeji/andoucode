package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.king.zxing.util.CodeUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.model.RestaurantOrderDetail;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

public class RestaurantOrderDetailActivity extends BaseActivity {

    @BindView(R.id.cancelBtn)
    View cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "订单详情");
        String id = getIntent().getStringExtra(KEY_DATA);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getOrderDetail(
                        LoginInfoUtil.getUid(),
                        id),
                result -> bindOrderDetail(result.data));
    }

    private void bindOrderDetail(RestaurantOrderDetail data) {
        if (data.status == RestaurantOrder.STATE.DAI_PING_JIA.stateInt
                || data.status == RestaurantOrder.STATE.YI_WAN_CHENG.stateInt) {
            findViewById(R.id.qrCodeContainer).setVisibility(View.GONE);
            findViewById(R.id.cancelBtnContainer).setVisibility(View.GONE);
        }
        Glide.with(mActivity)
                .load(UrlUtil.getImageUrl(data.logo_img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) findViewById(R.id.logoImg));
        Glide.with(mActivity)
                .load(CodeUtils.createQRCode(data.order_sn, SizeUtils.dp2px(150)))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) findViewById(R.id.qrCodeImg));
        ViewUtil.setText(mActivity, R.id.orderNumTxt, data.order_sn);
        ViewUtil.setText(mActivity, R.id.qrCodeTxt, data.order_sn);
        ViewUtil.setText(mActivity, R.id.dateTxt, data.orderingtime);
        ViewUtil.setText(mActivity, R.id.nameTxt, data.name);
        ViewUtil.setText(mActivity, R.id.dinnerDateTxt, data.dinnertime);
        ViewUtil.setText(mActivity, R.id.peopleCountTxt, data.people + "人");
        ViewUtil.setText(mActivity, R.id.psTxt, data.remark);
        ViewUtil.setText(mActivity, R.id.amountTxt, "￥" + data.prices);
        ViewUtil.setText(mActivity, R.id.payAmountTxt, "￥" + data.pay_money);
        ViewUtil.setText(mActivity, R.id.payWayTxt, getPayWayStr(data.method));
        for (Food food : data.foods) {
            RestaurantBillActivity.addFoodView(mActivity, findViewById(R.id.foodContainer), food);
        }
        cancelBtn.setOnClickListener(v ->
                RestaurantRefundActivity.start(mActivity, data.order_sn, 666));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private String getPayWayStr(int method) {
        if (method == 1) {
            return "微信支付";
        } else if (method == 4) {
            return "余额支付";
        }
        return "";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_order_detail;
    }

    public static void start(Activity activity, String id, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, id);
        ActivityUtils.startActivityForResult(bundle, activity, RestaurantOrderDetailActivity.class, requestCode);
    }
}
