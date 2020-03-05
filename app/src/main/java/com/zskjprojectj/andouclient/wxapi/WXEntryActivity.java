package com.zskjprojectj.andouclient.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.event.WeiXinLoginEvent;
import com.zskjprojectj.andouclient.utils.ConstantKt;

import org.greenrobot.eventbus.EventBus;

import kotlin.io.ConstantsKt;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI api = WXAPIFactory.createWXAPI(this, ConstantKt.WEI_XIN_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            WeiXinLoginEvent event = new WeiXinLoginEvent();
            event.code = ((SendAuth.Resp) resp).code;
            EventBus.getDefault().post(event);
        } else {
            ToastUtils.showLong("微信登录失败:" + resp.errStr);
        }
        finish();
    }
}