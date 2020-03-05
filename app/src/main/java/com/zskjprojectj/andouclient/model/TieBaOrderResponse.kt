package com.zskjprojectj.andouclient.model

class TieBaOrderResponse(val params: WxPay)
class WxPay(val partnerid: String,
            val prepayid: String,
            val noncestr: String,
            val timestamp: String,
            val sign: String
)
