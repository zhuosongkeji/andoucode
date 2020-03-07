package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity
import com.zskjprojectj.andouclient.activity.mall.MallDetailActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity
import com.zskjprojectj.andouclient.adapter.HistoryListAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : BaseActivity() {
    val adapter = HistoryListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "浏览痕迹")
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            when (item?.merchant_type_id) {
                MERCHANT_TYPE_MALL -> MallDetailActivity.start(item.id)
                MERCHANT_TYPE_HOTEL -> HotelDetailActivity.start(item.id)
                MERCHANT_TYPE_RESTAURTANT -> RestaurantDetailActivity.start(item.id)
            }
        }
        adapter.onSelectedStateChangedListener = BaseAdapter.OnSelectedStateChangedListener {
            selectAllBtn.isSelected = adapter.isSelectedAll
        }
        selectAllBtn.setOnClickListener {
            selectAllBtn.isSelected = !selectAllBtn.isSelected
            adapter.setSelectedAll(selectAllBtn.isSelected)
        }
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load({
            ApiUtils.getApiService().merchantrecord(
                    LoginInfoUtil.getUid(),
                    LoginInfoUtil.getToken()
                    , pageLoadUtil.page)
        }, { refresh, data ->
            if (!refresh) return@load
            if (data.dataList.isNullOrEmpty()) {
                ActionBarUtil.clearRightAction(mActivity)
            } else {
                val rightActionBtn = ActionBarUtil.setRightAction(mActivity, "管理", null)
                rightActionBtn.setOnClickListener {
                    rightActionBtn.isSelected = !rightActionBtn.isSelected
                    if (rightActionBtn.isSelected) {
                        rightActionBtn.text = "完成"
                        adapter.setSelectMode(BaseAdapter.SelectMode.MULTI)
                        delContainer.visibility = View.VISIBLE
                    } else {
                        rightActionBtn.text = "管理"
                        adapter.setSelectedAll(false)
                        adapter.setSelectMode(BaseAdapter.SelectMode.NONE)
                        delContainer.visibility = View.GONE
                    }
                }
                delBtn.setOnClickListener {
                    if (adapter.selectedItems.size <= 0) {
                        ToastUtils.showShort("请选择要删除的浏览记录!")
                    } else {
                        RequestUtil.request(mActivity, true, false,
                                {
                                    ApiUtils.getApiService().delHistory(
                                            LoginInfoUtil.getUid(),
                                            adapter.selectedItems.map { it.id.toInt() }.toTypedArray())
                                }, {
                            ToastUtils.showShort("删除成功!")
                            pageLoadUtil.refresh()
                            rightActionBtn.performClick()
                        })
                    }
                }
            }
        })
    }

    override fun getContentView() = R.layout.activity_history
}
