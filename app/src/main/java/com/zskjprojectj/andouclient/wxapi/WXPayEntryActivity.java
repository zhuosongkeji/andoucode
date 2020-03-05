package com.zskjprojectj.andouclient.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.activity.mall.MallPaySuccessActivity;
import com.zskjprojectj.andouclient.utils.ConstantKt;
import com.zskjprojectj.andouclient.utils.ToastUtil;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.wxapi
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/30 11:34
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;
    private String exData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, ConstantKt.WEI_XIN_APP_ID, false);


        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        if (baseReq instanceof PayReq) {
            exData = ((PayReq) baseReq).extData;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        finish();
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                if ("tiebapay".equals(exData)) {
                    MallPaySuccessActivity.start("tieba");
                } else {
                    startActivity(new Intent(WXPayEntryActivity.this, MallPaySuccessActivity.class));
                }
            } else if (baseResp.errCode == -1) {
                ToastUtil.showToast("支付错误" + baseResp.errStr);

            } else if (baseResp.errCode == -2) {
                ToastUtil.showToast("取消支付");
            }
        } else {
            ToastUtil.showToast(baseResp.errStr);
        }
    }
}
