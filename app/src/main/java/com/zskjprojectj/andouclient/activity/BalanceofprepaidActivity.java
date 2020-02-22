package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.zskjprojectj.andouclient.entity.WalletrecharBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 余额充值
 */
public class BalanceofprepaidActivity extends BaseActivity {
    private Button btn_confirm;
    private RecyclerView mRecycler;
    @BindView(R.id.recyclerView)
    RecyclerView mRvPayWAys;
    private String payId;
    private TextView tv_balanceofmoney, tv_phonenum;
    private final static int WXPAY = 1;
    private String moneynum, phonenum;
    private EditText moneyet;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_balanceofprepaid);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("余额充值");
    }

    @Override
    protected void initViews() {
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        tv_balanceofmoney = findViewById(R.id.tv_balanceofmoney);
        tv_phonenum = findViewById(R.id.tv_phonenum);
        moneyet = findViewById(R.id.et_money);
        EventBus.getDefault().register(this);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(payId))
                {
                    ToastUtil.showToast("请选择支付方式");
                    return;
                }
                int id = Integer.parseInt(payId);
                switch (id) {
                    case WXPAY:
                        if (TextUtils.isEmpty(moneyet.getText().toString())) {
                            ToastUtil.showToast("充值金额不能为空");
                            return;
                        }
                        HttpRxObservable.getObservable(ApiUtils.getApiService().MallWXPayWaysrecharge(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                moneyet.getText().toString().trim(),
                                phonenum,
                                payId

                        )).subscribe(new BaseObserver<WXPayBean>(mAt) {
                            @Override
                            public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                                startWXPay(wxPayBean);
//                                finish();
                            }
                        });
                        break;

                }

            }
        });
        HttpRxObservable.getObservable(ApiUtils.getApiService().walletrechar(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<WalletrecharBean>(mAt) {
            @Override
            public void onHandleSuccess(WalletrecharBean walletrecharBean) throws IOException {
                moneynum = walletrecharBean.getMoney();
                phonenum = walletrecharBean.getMobile();
                tv_balanceofmoney.setText(moneynum);
                tv_phonenum.setText(phonenum);
            }
        });
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

    @OnClick(R.id.btn_confirm)
    public void onconfirm() {
//        int id = Integer.parseInt(payId);
//        switch (id)
//        {
//            case WXPAY:
//                if (TextUtils.isEmpty(moneyet.getText().toString()))
//                {
//                    ToastUtil.showToast("充值金额不能为空");
//                    return;
//                }
//                HttpRxObservable.getObservable(ApiUtils.getApiService().MallWXPayWaysrecharge(
//                        LoginInfoUtil.getUid(),
//                        LoginInfoUtil.getToken(),
//                        moneyet.getText().toString().trim(),
//                        phonenum,
//                        payId
//
//                )).subscribe(new BaseObserver<WXPayBean>(mAt) {
//                    @Override
//                    public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
//                        startWXPay(wxPayBean);
//                        finish();
//                    }
//                });
//                break;
//
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paysucces(PaySuccessEvent paySuccessEvent) {
        finish();
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
