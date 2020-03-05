package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.mall.MallPaySuccessActivity
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.WxPay
import com.zskjprojectj.andouclient.utils.*
import kotlinx.android.synthetic.main.activity_tie_ba_pay.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TieBaPayActivity : BaseActivity() {
    private var payId: String? = null
    private val WXPAY = 1
    private val YUEPAY = 4
    var post_id = 0L
    var method = "1"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
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
            when {
                oneDayBtn.isSelected -> {
                    price.text = "¥10"
                }
                twoDayBtn.isSelected -> {
                    price.text = "¥20"
                }
                threeDayBtn.isSelected -> {
                    price.text = "¥30"
                }
            }
        }
        oneDayBtn.setOnClickListener(clickListener)
        twoDayBtn.setOnClickListener(clickListener)
        threeDayBtn.setOnClickListener(clickListener)
        oneDayBtn.performClick()
        pay.setOnClickListener {
            when {
                oneDayBtn.isSelected -> {
                    method = "1"
                }
                twoDayBtn.isSelected -> {
                    method = "2"
                }
                threeDayBtn.isSelected -> {
                    method = "3"
                }
            }
            if (TextUtils.isEmpty(payId)) {
                ToastUtils.showShort("请选择支付方式")
            } else {
                when (payId?.toInt()) {
                    WXPAY -> {
                        RequestUtil.request(mActivity, true, false,
                                {
                                    ApiUtils.getApiService().tieBaOrder(
                                            LoginInfoUtil.getUid(),
                                            post_id,
                                            method,
                                            payId)
                                },
                                { PayUtil.startWXPay(mActivity,it.data.params) })
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
                                                ApiUtils.getApiService().tieBaOrder(
                                                        LoginInfoUtil.getUid(),
                                                        post_id,
                                                        method,
                                                        payId)
                                            },
                                            {
                                                MallPaySuccessActivity.start("tieBa")
                                                setResult(Activity.RESULT_OK)
                                                finish()
                                            })
                                }
                                .show()
                    }
                }
            }
        }
    }

    private fun clearBtnState() {
        oneDayBtn.isSelected = false
        twoDayBtn.isSelected = false
        threeDayBtn.isSelected = false
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPaySuccess(event: PaySuccessEvent) {
        setResult(Activity.RESULT_OK)
        finish()
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

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
