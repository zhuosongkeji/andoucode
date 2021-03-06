package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.king.zxing.util.CodeUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.MapUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.math.BigDecimal;

import butterknife.BindView;

import static com.zskjprojectj.andouclient.utils.ConstantKt.KEY_DATA;

public class RestaurantOrderDetailActivity extends BaseActivity {

    @BindView(R.id.cancelBtn)
    View cancelBtn;

    @BindView(R.id.payTxtBtn)
    View payTxtBtn;

    @BindView(R.id.reviewTxtBtn)
    View reviewTxtBtn;

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

    private void bindOrderDetail(RestaurantOrder data) {
        findViewById(R.id.callBtn).setOnClickListener(v ->
                startActivity(IntentUtils.getDialIntent(data.tel)));
        findViewById(R.id.locationBtn).setOnClickListener(v -> {
            MapUtil.start(data.address, mActivity);
        });
        if (data.status == RestaurantOrder.STATE.DAI_SHI_YONG.stateInt) {
            findViewById(R.id.qrCodeContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.controlBtnContainer).setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setOnClickListener(v ->
                    RestaurantRefundActivity.Companion.start(mActivity, data.order_sn, 666));
        } else if (data.status == RestaurantOrder.STATE.DAI_ZHI_FU.stateInt) {
            findViewById(R.id.controlBtnContainer).setVisibility(View.VISIBLE);
            payTxtBtn.setVisibility(View.VISIBLE);
            payTxtBtn.setOnClickListener(v ->
                    RestaurantBillActivity.start(mActivity, data, 666));
        } else if (data.status == RestaurantOrder.STATE.DAI_PING_JIA.stateInt) {
            findViewById(R.id.controlBtnContainer).setVisibility(View.VISIBLE);
            reviewTxtBtn.setVisibility(View.VISIBLE);
            reviewTxtBtn.setOnClickListener(v ->
                    RestaurantReviewActivity.start(mActivity, data, 666));
        }
        Glide.with(mActivity)
                .load(UrlUtil.INSTANCE.getImageUrl(data.logo_img))
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
        ViewUtil.setText(mActivity, R.id.amountTxt, "¥" + data.prices);
        ViewUtil.setText(mActivity, R.id.useScoreCountTxt, Double.valueOf(data.integral) > 0 ? "-" + data.integral : data.integral);
        if (TextUtils.isEmpty(data.pay_money)) {
            ViewUtil.setText(mActivity, R.id.payAmountTxt, "¥" + FormatUtil.getMoneyString(new BigDecimal(data.prices).subtract(new BigDecimal(data.integral)).doubleValue()));
        } else {
            ViewUtil.setText(mActivity, R.id.payAmountTxt, "¥" + FormatUtil.getMoneyString(new BigDecimal(data.pay_money).doubleValue()));
        }
        ViewUtil.setText(mActivity, R.id.payWayTxt, getPayWayStr(data.method));
        for (Food food : data.foods) {
            RestaurantBillActivity.addFoodView(mActivity, findViewById(R.id.foodContainer), food);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private String getPayWayStr(String method) {
        if ("1".equals(method)) {
            return "微信支付";
        } else if ("4".equals(method)) {
            return "余额支付";
        }
        return "未支付";
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
