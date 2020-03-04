package com.zskjprojectj.andouclient.activity.mall

import android.os.Bundle
import android.util.Log
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.event.OrderStateChangedEvent
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_mall_order_cancel.*
import org.greenrobot.eventbus.EventBus


class MallOrderCancelActivity : BaseActivity() {

    private var reasonId: String? = ""
    private var cancelType: String? = null
    var stringArrayList: ArrayList<String> = arrayListOf<String>("我不想买了", "信息填写错误重新拍", "卖家缺货", "其他原因")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "取消订单")
        val orderId = intent.getStringExtra("order_id")
        Log.d("wangbin",orderId)
        rv_refund_reason.setOnClickListener {
            //条件选择器
            val pvOptions: OptionsPickerView<String> = OptionsPickerBuilder(mActivity, OnOptionsSelectListener { options1, option2, options3, v ->
                reasonTxt.text = (stringArrayList[options1])
                cancelType = (stringArrayList[options1])
                reasonId = (options1 + 1).toString()
            }).build<String>()
            pvOptions.setPicker(stringArrayList)
            pvOptions.show()
        }
        btn_commint.setOnClickListener {
            if ("" == reasonId) {
                ToastUtils.showShort("请选择取消订单的原因")
            } else {
                RequestUtil.request(mActivity, true, false,
                        {
                            ApiUtils.getApiService().cancelOrder(
                                    LoginInfoUtil.getUid(),
                                    orderId.toLong(),
                                    reasonId,
                                    et_dec.text.toString()
                            )
                        },
                        {
                            ToastUtils.showShort("取消订单成功")
                            EventBus.getDefault().post(OrderStateChangedEvent())
                            finish()
                        })
            }
        }

    }

    companion object {
        @JvmStatic
        fun start(order_id: String) {
            val bundle = Bundle()
            bundle.putString("order_id", order_id)
            ActivityUtils.startActivity(bundle, MallOrderCancelActivity::class.java)
        }
    }

    override fun getContentView() = R.layout.activity_mall_order_cancel
}
