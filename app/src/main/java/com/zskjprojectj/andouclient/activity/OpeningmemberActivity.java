package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayUtil;

import butterknife.BindView;

/**
 * 开通会员界面
 */
public class OpeningmemberActivity extends BaseActivity {
    @BindView(R.id.rv_pay_ways)
    RecyclerView mRvPayWAys;
    private String payId;
    private final static int WXPAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "开通会员");
        RecyclerView mRecycler = findViewById(R.id.rv_pay_ways);
        TextView tv_openprice = findViewById(R.id.tv_openprice);
        WebView wv_webview = findViewById(R.id.wv_webview);
        //请求信息
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().vip_rote(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()),
                result -> {
                    tv_openprice.setText("¥" + result.data.getPrice());
                    String details = result.data.getVip_rote();
                    String htmlData = getHtmlData(details);
                    System.out.println("tml" + htmlData);
                    wv_webview.loadData(htmlData, "text/html", "UTF-8");
                    wv_webview.setVerticalScrollBarEnabled(false);
                    wv_webview.setHorizontalScrollBarEnabled(false);
                });

        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        //请求支付方式
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getWalletPayWays(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()),
                result1 -> {
                    mRvPayWAys.setLayoutManager(new LinearLayoutManager(mActivity));
                    PayWaysAdapter adapter = new PayWaysAdapter(R.layout.pay_ways_item, result1.data);
                    mRvPayWAys.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
                    mRvPayWAys.setAdapter(adapter);
                    adapter.setItemPayWays(new PayWaysAdapter.ItemPayWays() {
                        @Override
                        public void getPayWays(String payWays, int position) {
                            payId = result1.data.get(position).getId();
                        }
                    });
                });

        Button btn_sure = findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(payId)) {
                    ToastUtils.showShort("请选择支付方式");
                    return;
                }
                int id = Integer.parseInt(payId);
                switch (id) {
                    case WXPAY:
                        RequestUtil.request(mActivity, true, false,
                                () -> ApiUtils.getApiService().vip_recharge(
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken(),
                                        payId
                                ),
                                result -> {
                                    PayUtil.INSTANCE.startWXPay(mActivity, result.data);
                                });
                        break;
                }
            }
        });

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

    @Override
    protected int getContentView() {
        return R.layout.activity_openingmember;
    }
}
