package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.PaySuccessEvent
import com.zskjprojectj.andouclient.utils.PayUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_balanceofprepaid.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BalanceofprepaidActivity : BaseActivity() {
    private var payId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "余额充值")
        EventBus.getDefault().register(this)
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().walletrechar(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                { result ->
                    tv_balanceofmoney.text = result.data.money
                    tv_phonenum.text = result.data.mobile
                    btn_confirm.setOnClickListener(View.OnClickListener {
                        if (TextUtils.isEmpty(payId)) {
                            ToastUtil.showToast("请选择支付方式")
                            return@OnClickListener
                        }
                        when (payId) {
                            "1" -> {
                                if (TextUtils.isEmpty(et_money.text.toString())) {
                                    ToastUtil.showToast("充值金额不能为空")
                                    return@OnClickListener
                                }
                                RequestUtil.request(mActivity, true, false,
                                        {
                                            ApiUtils.getApiService().MallWXPayWaysrecharge(
                                                    LoginInfoUtil.getUid(),
                                                    LoginInfoUtil.getToken(),
                                                    et_money.text.toString().trim { it <= ' ' },
                                                    result.data.mobile,
                                                    payId
                                            )
                                        },
                                        { result -> PayUtil.startWXPay(mActivity, result.data) }
                                )
                            }
                        }
                    })
                }
        )
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().getWalletPayWays(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                { result ->
                    val adapter = PayWaysAdapter(R.layout.pay_ways_item, result.data)
                    recyclerView.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
                    recyclerView.adapter = adapter
                    adapter.setItemPayWays { _, position -> payId = adapter.data[position].id }
                }
        )
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun paysucces(paySuccessEvent: PaySuccessEvent?) {
        finish()
    }

    override fun getContentView() = R.layout.activity_balanceofprepaid
}