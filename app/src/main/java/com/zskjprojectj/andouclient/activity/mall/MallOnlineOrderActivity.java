package com.zskjprojectj.andouclient.activity.mall;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MallOrderListActivity;
import com.zskjprojectj.andouclient.activity.MyAddressActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallBuyInfoAdapter;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.entity.mall.MallSettlementBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayCancle;
import com.zskjprojectj.andouclient.utils.PaySuccessBackEvent;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.PayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class MallOnlineOrderActivity extends BaseActivity {


    //结算收货人
    @BindView(R.id.tv_client_name)
    TextView mTvClientName;

    //结算收货人电话
    @BindView(R.id.tv_client_phone)
    TextView mTvClientPhone;

    //结算收货人地址
    @BindView(R.id.tv_client_address)
    TextView mTvClientAddress;

    //运费
    @BindView(R.id.tv_online_order_shipping_free)
    TextView mShippingFree;

    //合计钱数
    @BindView(R.id.tv_order_money)
    TextView mTvOrderMoney;

    @BindView(R.id.mall_order_money)
    TextView mMallOrderMoney;

    @BindView(R.id.rv_pay_ways)
    RecyclerView mRvPayWAys;

    @BindView(R.id.rv_info_recycler)
    RecyclerView mRvInfoRecycler;

    @BindView(R.id.cb_selectorcb1)
    AppCompatCheckBox cbSelector;

    @BindView(R.id.tv_integral)
    TextView mIvIntegral;


    private String order_sn;
    private String puzzle_id;
    private String open_join;
    private String group_id;
    private String payId;
    private final static int WXPAY = 1;
    private final static int YUEPAY = 4;
    private String is_integral = "0";
    private final int SELECTORADDRESS = 1;

    @OnClick(R.id.rl_selector_address)
    public void clickAddress() {
        Intent intent = new Intent(MallOnlineOrderActivity.this, MyAddressActivity.class);

        startActivityForResult(intent, SELECTORADDRESS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(MallOnlineOrderActivity.this);
        ActionBarUtil.setTitle(mActivity, "在线下单");
        order_sn = getIntent().getStringExtra("order_sn");
        puzzle_id = getIntent().getStringExtra("puzzle_id");
        open_join = getIntent().getStringExtra("open_join");
        group_id = getIntent().getStringExtra("group_id");
        mRvInfoRecycler.setLayoutManager(new LinearLayoutManager(this));

        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().MallSettlement(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        order_sn
                ),
                result -> {
                    MallBuyInfoAdapter adapter = new MallBuyInfoAdapter(R.layout.buy_info_item, result.data.getDetails());
                    mRvInfoRecycler.setAdapter(adapter);
                    //收货信息
                    MallSettlementBean.UserinfoBean userinfo = result.data.getUserinfo();

                    mTvClientName.setText(userinfo.getName());
                    mTvClientPhone.setText(userinfo.getMobile());

                    String shippingAddress = userinfo.getProvince() + " " + userinfo.getCity() + " " + userinfo.getArea() + " " + userinfo.getAddress();
                    mTvClientAddress.setText(shippingAddress);
                    mShippingFree.setText(result.data.getShipping_free());

                    mTvOrderMoney.setText("¥" + result.data.getOrder_money());
                    mMallOrderMoney.setText("¥" + result.data.getOrder_money());
                    int score = Integer.parseInt(result.data.getIntegral());
                    if (score == 0) {
                        cbSelector.setEnabled(false);
                    }
                    mIvIntegral.setText(result.data.getIntegral());

                    cbSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                BigDecimal bigDecimal = new BigDecimal(result.data.getOrder_money());
                                BigDecimal subtract = bigDecimal.subtract(new BigDecimal(result.data.getIntegral()));
                                mTvOrderMoney.setText("¥" + subtract.toString());
                                mMallOrderMoney.setText("¥" + subtract.toString());
                                is_integral = "1";
                            } else {
                                mTvOrderMoney.setText("¥" + result.data.getOrder_money());
                                mMallOrderMoney.setText("¥" + result.data.getOrder_money());
                                is_integral = "0";
                            }
                        }
                    });

                });

        //请求支付方式
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getMallPayWays(),
                result -> {
                    PayWaysAdapter adapter = new PayWaysAdapter(R.layout.pay_ways_item, result.data);
                    mRvPayWAys.setAdapter(adapter);
                    adapter.setItemPayWays(new PayWaysAdapter.ItemPayWays() {
                        @Override
                        public void getPayWays(String payWays, int position) {
                            payId = result.data.get(position).getId();
                        }
                    });
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new PayCancle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MallOnlineOrderActivity.this);
    }

    public static void start(String order_sn, String puzzle_id, String open_join, String group_id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_sn", order_sn);
        bundle.putSerializable("puzzle_id", puzzle_id);
        bundle.putSerializable("open_join", open_join);
        bundle.putSerializable("group_id", group_id);
        ActivityUtils.startActivity(bundle, MallOnlineOrderActivity.class);
    }

    @OnClick(R.id.ll_buy_pay)
    public void clickBuyPay() {
        if (TextUtils.isEmpty(payId)) {
            ToastUtils.showShort("请选择支付方式");
            return;
        }

        int id = Integer.parseInt(payId);
        switch (id) {
            case WXPAY:
                RequestUtil.request(mActivity,true,false,
                        ()->ApiUtils.getApiService().MallWXPayWays(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                order_sn,
                                payId,
                                is_integral,
                                puzzle_id,
                                open_join,
                                group_id
                        ),
                        result -> PayUtil.INSTANCE.startWXPay(mActivity, result.data));

                break;
            case YUEPAY:
                new AlertDialog.Builder(mActivity)
                        .setTitle("温馨提示")
                        .setMessage("确定用余额支付该订单吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定",
                                (dialog, which) -> {

                            RequestUtil.request(mActivity,true,false,
                                    ()->ApiUtils.getApiService().MallWXPayWays(
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken(),
                                            order_sn,
                                            payId,
                                            is_integral,
                                            puzzle_id,
                                            open_join,
                                            group_id
                                    ),
                                    result -> ActivityUtils.startActivity(mActivity,MallPaySuccessActivity.class));
                                })
                        .show();


                break;
        }

    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentEventBus(PaySuccessEvent paySuccessEvent) {
        Intent intent = new Intent(MallOnlineOrderActivity.this, MallOrderListActivity.class);
        intent.putExtra("flag", "MallPaySuccess");
        startActivity(intent);
        finish();
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backEventBus(PaySuccessBackEvent paySuccessBackEvent) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECTORADDRESS) {
                String name = data.getStringExtra("name");
                mTvClientName.setText(name);
                String address = data.getStringExtra("address");
                mTvClientAddress.setText(address);
                String tel = data.getStringExtra("tel");
                mTvClientPhone.setText(tel);
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mall_online_order;
    }
}
