package com.zskjprojectj.andouclient.activity.mall;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MyaddressActivity;
import com.zskjprojectj.andouclient.activity.ShoporderActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallBuyInfoAdapter;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.entity.mall.MallSettlementBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayCancle;
import com.zskjprojectj.andouclient.utils.PaySuccessBackEvent;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
    private String payId;
    private final static int WXPAY = 1;
    private final static int YUEPAY = 4;
    private String is_integral = "0";
    private final int SELECTORADDRESS=1;

    @OnClick(R.id.rl_selector_address)
    public void clickAddress(){
        Intent intent=new Intent(MallOnlineOrderActivity.this, MyaddressActivity.class);

        startActivityForResult(intent,SELECTORADDRESS);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_online_order);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//1、注册广播
        EventBus.getDefault().register(MallOnlineOrderActivity.this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //2、解除注册
        EventBus.getDefault().unregister(MallOnlineOrderActivity.this);
    }


    @Override
    protected void initViews() {
        order_sn = getIntent().getStringExtra("order_sn");
        Log.d("wangbin", "initViews: " + order_sn);
        topView.setTitle("在线下单");
        topView.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new PayCancle());
                finish();
            }
        });
        getBarDistance(topView);

        mRvInfoRecycler.setLayoutManager(new LinearLayoutManager(this));


    }

    public static void start(String order_sn) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_sn", order_sn);
        ActivityUtils.startActivity(bundle, MallOnlineOrderActivity.class);
    }

    @Override
    public void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().MallSettlement(

                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                order_sn
        )).subscribe(new BaseObserver<MallSettlementBean>(mAt) {
            @Override
            public void onHandleSuccess(MallSettlementBean mallSettlementBean) throws IOException {

                MallBuyInfoAdapter adapter = new MallBuyInfoAdapter(R.layout.buy_info_item, mallSettlementBean.getDetails());
                mRvInfoRecycler.setAdapter(adapter);
                //收货信息
                MallSettlementBean.UserinfoBean userinfo = mallSettlementBean.getUserinfo();

                mTvClientName.setText(userinfo.getName());
                mTvClientPhone.setText(userinfo.getMobile());

                String shippingAddress = userinfo.getProvince() + " " + userinfo.getCity() + " " + userinfo.getArea() + " " + userinfo.getAddress();
                mTvClientAddress.setText(shippingAddress);
                mShippingFree.setText(mallSettlementBean.getShipping_free());

                mTvOrderMoney.setText("¥" + mallSettlementBean.getOrder_money());
                mMallOrderMoney.setText("¥" + mallSettlementBean.getOrder_money());
                mIvIntegral.setText(mallSettlementBean.getIntegral());

                cbSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            BigDecimal bigDecimal = new BigDecimal(mallSettlementBean.getOrder_money());
                            BigDecimal subtract = bigDecimal.subtract(new BigDecimal(mallSettlementBean.getIntegral()));
                            mTvOrderMoney.setText("¥" + subtract.toString());
                            mMallOrderMoney.setText("¥" + subtract.toString());
                            is_integral = "1";
                        } else {
                            mTvOrderMoney.setText("¥" + mallSettlementBean.getOrder_money());
                            mMallOrderMoney.setText("¥" + mallSettlementBean.getOrder_money());
                            is_integral = "0";
                        }
                    }
                });

            }
        });

        //请求支付方式
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallPayWays()).subscribe(new BaseObserver<List<MallPayWaysBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MallPayWaysBean> mallPayWaysBeans) throws IOException {
                mRvPayWAys.setLayoutManager(new LinearLayoutManager(mAt));
                PayWaysAdapter adapter = new PayWaysAdapter(R.layout.pay_ways_item, mallPayWaysBeans);
                mRvPayWAys.addItemDecoration(new DividerItemDecoration(mAt, DividerItemDecoration.VERTICAL));
                mRvPayWAys.setAdapter(adapter);
                adapter.setItemPayWays(new PayWaysAdapter.ItemPayWays() {
                    @Override
                    public void getPayWays(String payWays, int position) {
                        payId = mallPayWaysBeans.get(position).getId();
                    }
                });
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.ll_buy_pay)
    public void clickBuyPay() {
        if (TextUtils.isEmpty(payId)) {
            ToastUtil.showToast("请选择支付方式");
            return;
        }

        int id = Integer.parseInt(payId);
        switch (id) {
            case WXPAY:
                HttpRxObservable.getObservable(ApiUtils.getApiService().MallWXPayWays(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        order_sn,
                        payId,
                        is_integral
                )).subscribe(new BaseObserver<WXPayBean>(mAt) {
                    @Override
                    public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                        startWXPay(wxPayBean);
                    }
                });
                break;
            case YUEPAY:
                new AlertDialog.Builder(mAt)
                        .setTitle("温馨提示")
                        .setMessage("确定用余额支付该订单吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定",
                                (dialog, which) -> {
                                    HttpRxObservable.getObservable(ApiUtils.getApiService().MallWXPayWays(
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken(),
                                            order_sn,
                                            payId,
                                            is_integral
                                    )).subscribe(new BaseObserver<WXPayBean>(mAt) {
                                        @Override
                                        public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                                            Intent intent = new Intent(MallOnlineOrderActivity.this, MallPaySuccessActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                })
                        .show();


                break;
        }

    }
    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentEventBus(PaySuccessEvent paySuccessEvent) {
        Intent intent = new Intent(MallOnlineOrderActivity.this, ShoporderActivity.class);
        intent.putExtra("flag", "MallPaySuccess");
        startActivity(intent);
        finish();
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backEventBus(PaySuccessBackEvent paySuccessBackEvent) {

        finish();
    }

    private void startWXPay(WXPayBean wxPayBean) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(MallOnlineOrderActivity.this, wxPayBean.getAppid());
        //将该app注册到微信
        msgApi.registerApp(wxPayBean.getAppid());

//        创建支付请求对象
        PayReq req = new PayReq();
        req.appId = wxPayBean.getAppid();
        req.partnerId = wxPayBean.getMch_id();
        req.prepayId = wxPayBean.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = wxPayBean.getNonce_str();
        req.timeStamp = wxPayBean.getTimestamp();
        req.sign = wxPayBean.getSign();
        msgApi.sendReq(req);
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = "测试分享";
//
////用 WXTextObject 对象初始化一个 WXMediaMessage 对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        msg.description = "测试分享";
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "text";
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
////调用api接口，发送数据到微信
//        msgApi.sendReq(req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==SELECTORADDRESS){
                String name = data.getStringExtra("name");
                mTvClientName.setText(name);
                String address = data.getStringExtra("address");
                mTvClientAddress.setText(address);
                String tel = data.getStringExtra("tel");
                mTvClientPhone.setText(tel);
                Log.d(TAG, "onActivityResult: "+name+address+tel);
            }
        }
    }
}
