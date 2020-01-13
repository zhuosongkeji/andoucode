package com.zskjprojectj.andouclient.utils;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.entity.WXPayBean;

public class PayUtil {
    public static void startWXPay(Context context, WXPayBean wxPayBean) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, wxPayBean.getAppid());
        msgApi.registerApp(wxPayBean.getAppid());
        PayReq req = new PayReq();
        req.appId = wxPayBean.getAppid();
        req.partnerId = wxPayBean.getMch_id();
        req.prepayId = wxPayBean.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = wxPayBean.getNonce_str();
        req.timeStamp = wxPayBean.getTimestamp();
        req.sign = wxPayBean.getSign();
        msgApi.sendReq(req);
    }
}
