package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.entity.mall.MallSettlementBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MallOnlineOrderActivity extends BaseActivity {

    //结算图片
    @BindView(R.id.iv_online_order_image)
    ImageView mIvOnlinOrderImage;

    //结算名称
    @BindView(R.id.tv_online_order_name)
    TextView mTvOnlineOrderName;

    //结算价格
    @BindView(R.id.tv_online_order_price)
    TextView TvOnlineOrderPrice;

    //结算规格
    @BindView(R.id.tv_online_order_option)
    TextView TvOnlineOrderOption;

    //结算数量
    @BindView(R.id.tv_online_order_number)
    TextView TvOnlineOrderNumber;

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



    private String order_sn;
    private String payId;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_online_order);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }

    @Override
    protected void initViews() {
        order_sn = getIntent().getStringExtra("order_sn");
        topView.setTitle("在线下单");
        getBarDistance(topView);

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
                MallSettlementBean.DetailsBean detailsBean = mallSettlementBean.getDetails().get(0);
                Glide.with(mAt).load(BaseUrl.BASE_URL+detailsBean.getImg()).into(mIvOnlinOrderImage);
                mTvOnlineOrderName.setText(detailsBean.getName());
                TvOnlineOrderPrice.setText("¥"+detailsBean.getPrice());

                StringBuffer buffer=new StringBuffer();
                List<String> attr_value = detailsBean.getAttr_value();
                for (String s : attr_value) {
                    buffer.append(s).append("-");
                }
                String stringOption = buffer.substring(0, buffer.length() - 1);
                TvOnlineOrderOption.setText(stringOption);
                TvOnlineOrderNumber.setText("X"+detailsBean.getNum());
                //收货信息
                MallSettlementBean.UserinfoBean userinfo = mallSettlementBean.getUserinfo();

                mTvClientName.setText(userinfo.getName());
                mTvClientPhone.setText(userinfo.getMobile());

                String shippingAddress = userinfo.getProvince() + " " + userinfo.getCity() + " " + userinfo.getArea() + " " + userinfo.getAddress();
                mTvClientAddress.setText(shippingAddress);
                mShippingFree.setText(mallSettlementBean.getShipping_free());
                Log.d(TAG, "购买结算页: "+mallSettlementBean.getShipping_free());

                mTvOrderMoney.setText(mallSettlementBean.getOrder_money());
                mMallOrderMoney.setText(mallSettlementBean.getOrder_money());
            }
        });


        //请求支付方式
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallPayWays()).subscribe(new BaseObserver<List<MallPayWaysBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MallPayWaysBean> mallPayWaysBeans) throws IOException {
                mRvPayWAys.setLayoutManager(new LinearLayoutManager(mAt));
                PayWaysAdapter adapter=new PayWaysAdapter(R.layout.pay_ways_item,mallPayWaysBeans);
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
    public void clickBuyPay(){

        HttpRxObservable.getObservable(ApiUtils.getApiService().MallWXPayWays(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                order_sn,
                payId,
                "0"

        )).subscribe(new BaseObserver<WXPayBean>(mAt) {
            @Override
            public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                startWXPay(wxPayBean);
            }
        });

    }

    private void startWXPay(WXPayBean wxPayBean){
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(MallOnlineOrderActivity.this, wxPayBean.getAppid());
        //将该app注册到微信
        msgApi.registerApp(wxPayBean.getAppid());

        //创建支付请求对象
        PayReq req=new PayReq();
        req.appId = wxPayBean.getAppid();
        req.partnerId = wxPayBean.getMch_id();
        req.prepayId= wxPayBean.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr= wxPayBean.getNonce_str();
        req.timeStamp=wxPayBean.getTimestamp();
        req.sign= wxPayBean.getSign();
        msgApi.sendReq(req);
    }

}
