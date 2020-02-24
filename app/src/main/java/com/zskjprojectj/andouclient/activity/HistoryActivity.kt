package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity
import com.zskjprojectj.andouclient.adapter.HistoryAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.MERCHANT_TYPE_HOTEL
import com.zskjprojectj.andouclient.utils.MERCHANT_TYPE_MALL
import com.zskjprojectj.andouclient.utils.MERCHANT_TYPE_RESTAURTANT
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : BaseActivity() {
    val adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "浏览痕迹")
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            when (item?.merchant_type_id) {
                MERCHANT_TYPE_MALL -> MallShoppingHomeActivity.start(item.id)
                MERCHANT_TYPE_HOTEL -> HotelDetailActivity.start(item.id)
                MERCHANT_TYPE_RESTAURTANT -> RestaurantDetailActivity.start(item.id)
            }
        }
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().merchantrecord(
                    LoginInfoUtil.getUid(),
                    LoginInfoUtil.getToken()
                    , pageLoadUtil.page)
        }
    }

    override fun getContentView() = R.layout.activity_history
}
