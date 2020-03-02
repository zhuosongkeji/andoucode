package com.zskjprojectj.andouclient.model

import com.zskjprojectj.andouclient.entity.WXPayBean

class TieBaOrderResponse(val params: WxPay)
class WxPay(val partnerid: String,
            val prepayid: String,
            val noncestr: String,
            val timestamp: String,
            val sign: String
)
