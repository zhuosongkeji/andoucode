package com.zskjprojectj.andouclient.utils

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zskjprojectj.andouclient.model.WxPay

object PayUtil {
    fun startWXPay(context: Context, wxPayBean: WxPay) {
        val msgApi = WXAPIFactory.createWXAPI(context, WEI_XIN_APP_ID)
        msgApi.registerApp(WEI_XIN_APP_ID)
        if (!msgApi.isWXAppInstalled) {
            ToastUtils.showLong("您还没有安装微信")
            return
        }
        val req = PayReq()
        req.appId = WEI_XIN_APP_ID
        req.partnerId = wxPayBean.partnerid
        req.prepayId = wxPayBean.prepayid
        req.packageValue = "Sign=WXPay"
        req.nonceStr = wxPayBean.noncestr
        req.timeStamp = wxPayBean.timestamp
        req.sign = wxPayBean.sign
        msgApi.sendReq(req)
    }
}