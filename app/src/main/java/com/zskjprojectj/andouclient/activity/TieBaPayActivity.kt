package com.zskjprojectj.andouclient.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.ToolbarWidgetWrapper
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.mall.MallPaySuccessActivity
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter
import com.zskjprojectj.andouclient.entity.WXPayBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_tie_ba_pay.*

class TieBaPayActivity : BaseActivity() {
    private var payId: String? = null
    private val WXPAY = 1
    private val YUEPAY = 4
    var post_id = 0L
    var method = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "置顶选择")
        post_id = intent.getLongExtra(KEY_DATA, 0L)
        RequestUtil.request(mActivity, true, false,
                { ApiUtils.getApiService().mallPayWays },
                {
                    val adapter = PayWaysAdapter(R.layout.pay_ways_item, it.data)
                    adapter.bindToRecyclerView(recyclerView)
                    adapter.setItemPayWays { _, position ->
                        payId = it.data[position].id
                    }
                })
        val clickListener = View.OnClickListener {
            clearBtnState()
            if (!it.isSelected)
                it.isSelected = true
        }
        oneDayBtn.setOnClickListener(clickListener)
        twoDayBtn.setOnClickListener(clickListener)
        threeDayBtn.setOnClickListener(clickListener)

        pay.setOnClickListener {
            when {
                oneDayBtn.isSelected -> {
                    method = "1"
                }
                twoDayBtn.isSelected -> {
                    method = "2"
                }
                twoDayBtn.isSelected -> {
                    method = "3"
                }
            }
            if (TextUtils.isEmpty(payId)) {
                ToastUtil.showToast("请选择支付方式")
            } else {
                RequestUtil.request(mActivity, true, false,
                        {
                            ApiUtils.getApiService().tieBaOrder(
                                    LoginInfoUtil.getUid(),
                                    post_id,
                                    method,
                                    payId)
                        },
                        {
                            when (payId?.toInt()) {
                                WXPAY -> {
                                    RequestUtil.request(mActivity, true, false,
                                            {
                                                ApiUtils.getApiService().MallWXPayWays(
                                                        LoginInfoUtil.getUid(),
                                                        LoginInfoUtil.getToken(),
                                                        it.data.oder_sn,
                                                        payId)
                                            },
                                            { startWXPay(it.data) })
                                }
                                YUEPAY -> {
                                    AlertDialog.Builder(mActivity)
                                            .setTitle("温馨提示")
                                            .setMessage("确定用余额支付该订单吗？")
                                            .setNegativeButton("取消", null)
                                            .setPositiveButton("确定"
                                            ) { _: DialogInterface?, _: Int ->
                                                RequestUtil.request(mActivity, true, false,
                                                        {
                                                            ApiUtils.getApiService().MallWXPayWays(
                                                                    LoginInfoUtil.getUid(),
                                                                    LoginInfoUtil.getToken(),
                                                                    it.data.oder_sn,
                                                                    payId)
                                                        },
                                                        {
                                                            ActivityUtils.startActivity(MallPaySuccessActivity::class.java)
                                                            setResult(Activity.RESULT_OK)
                                                            finish()
                                                        })
                                            }
                                            .show()
                                }
                            }
                        })
            }
        }
    }

    private fun clearBtnState() {
        oneDayBtn.isSelected = false
        twoDayBtn.isSelected = false
        threeDayBtn.isSelected = false
    }

    private fun startWXPay(wxPayBean: WXPayBean) {
        val msgApi = WXAPIFactory.createWXAPI(mActivity, wxPayBean.appid)
        //将该app注册到微信
        msgApi.registerApp(wxPayBean.appid)
        //        创建支付请求对象
        val req = PayReq()
        req.appId = wxPayBean.appid
        req.partnerId = wxPayBean.mch_id
        req.prepayId = wxPayBean.prepay_id
        req.packageValue = "Sign=WXPay"
        req.nonceStr = wxPayBean.nonce_str
        req.timeStamp = wxPayBean.timestamp
        req.sign = wxPayBean.sign
        msgApi.sendReq(req)
    }

    companion object {
        fun start(activity: BaseActivity, post_id: Long?, requestCode: Int) {
            ActivityUtils.startActivityForResult(
                    bundleOf(Pair(KEY_DATA, post_id)),
                    activity,
                    TieBaPayActivity::class.java,
                    requestCode)
        }
    }

    override fun getContentView() = R.layout.activity_tie_ba_pay
}
