package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 开通会员界面
 */
public class OpeningmemberActivity extends BaseActivity {
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @BindView(R.id.rv_pay_ways)
    RecyclerView mRvPayWAys;
    private String payId;
    private RecyclerView mRecycler;
    private final static int WXPAY = 1;
    private Button btn_sure;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_openingmember);
    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("开通会员");
        mRecycler = findViewById(R.id.rv_pay_ways);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        btn_sure=findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(payId);
                switch (id)
                {
                    case WXPAY:
                        HttpRxObservable.getObservable(ApiUtils.getApiService().vip_recharge(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                payId

                        )).subscribe(new BaseObserver<WXPayBean>(mAt) {
                            @Override
                            public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                                startWXPay(wxPayBean);
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {
        //请求支付方式
        HttpRxObservable.getObservable(ApiUtils.getApiService().getWalletPayWays(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<List<MallPayWaysBean>>(mAt) {
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

    private void startWXPay(WXPayBean wxPayBean) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(mAt, wxPayBean.getAppid());
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
    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }
}