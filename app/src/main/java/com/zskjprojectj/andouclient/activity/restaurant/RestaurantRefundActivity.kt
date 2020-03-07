package com.zskjprojectj.andouclient.activity.restaurant

import android.app.Activity
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.model.BaseResult
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_restaurant_refund.*

class RestaurantRefundActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "取消预约")
        submitBtn.setOnClickListener {
            val reasonStr = reasonEdt.text.toString()
            if (reasonStr.isEmpty()) {
                ToastUtils.showShort("请输入取消原因!")
            } else {
                RequestUtil.request(mActivity, true, false,
                        {
                            ApiUtils.getApiService().refund(
                                    LoginInfoUtil.getUid(),
                                    intent.getStringExtra(KEY_DATA),
                                    reasonEdt!!.text.toString()
                            )
                        },
                        { result: BaseResult<*> ->
                            ToastUtils.showShort(result.msg)
                            setResult(Activity.RESULT_OK)
                            finish()
                        })
            }
        }
    }

    override fun getContentView() = R.layout.activity_restaurant_refund

    companion object {
        fun start(activity: Activity, id: String?, requestCode: Int) {
            val bundle = Bundle()
            bundle.putString(KEY_DATA, id)
            ActivityUtils.startActivityForResult(bundle, activity, RestaurantRefundActivity::class.java, requestCode)
        }
    }
}