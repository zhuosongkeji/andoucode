package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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
import com.zskjprojectj.andouclient.entity.ViproteBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.WxPay;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 开通会员界面
 */
public class OpeningmemberActivity extends BaseActivity {
    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;
    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;
    @BindView(R.id.rv_pay_ways)
    RecyclerView mRvPayWAys;
    private String payId;
    private RecyclerView mRecycler;
    private final static int WXPAY = 1;
    private Button btn_sure;
    private TextView tv_openprice;
    private WebView wv_webview;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_openingmember);
    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("开通会员");
        mRecycler = findViewById(R.id.rv_pay_ways);
        tv_openprice = findViewById(R.id.tv_openprice);
        wv_webview = findViewById(R.id.wv_webview);
        //请求信息
        HttpRxObservable.getObservable(ApiUtils.getApiService().vip_rote(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<ViproteBean>(mAt) {
            @Override
            public void onHandleSuccess(ViproteBean viproteBean) throws IOException {
                tv_openprice.setText("¥" + viproteBean.getPrice());
                String details = viproteBean.getVip_rote();
                Log.d(TAG, "onHandleSuccess:+ " + details);
                String htmlData = getHtmlData(details);
                System.out.println("tml" + htmlData);
                wv_webview.loadData(htmlData, "text/html", "UTF-8");
                wv_webview.setVerticalScrollBarEnabled(false);
                wv_webview.setHorizontalScrollBarEnabled(false);
            }
        });
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        btn_sure = findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(payId)) {
                    ToastUtil.showToast("请选择支付方式");
                    return;
                }
                int id = Integer.parseInt(payId);
                switch (id) {
                    case WXPAY:
                        HttpRxObservable.getObservable(ApiUtils.getApiService().vip_recharge(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                payId

                        )).subscribe(new BaseObserver<WxPay>(mAt) {
                            @Override
                            public void onHandleSuccess(WxPay wxPay) throws IOException {
                                PayUtil.INSTANCE.startWXPay(mAt, wxPay);
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

    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }

    /**
     * 拼接html字符串片段
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body style:'height:auto;max-width: 100%; width:auto;'>" + bodyHTML + "</body></html>";
    }
}
