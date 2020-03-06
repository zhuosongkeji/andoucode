package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.model.BaseResult
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity
import com.zskjprojectj.andouclient.adapter.MyFollowAdapter
import com.zskjprojectj.andouclient.entity.MyFocusonBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_my_follow.*

class MyFollowActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "店铺关注")
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        loadData()
    }

    private fun loadData() {
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().usersfollow(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) }) { result: BaseResult<out List<MyFocusonBean>> ->
            val adapter = MyFollowAdapter(R.layout.item_focuson, result.data)
            adapter.setEmptyView(R.layout.layout_empty_view, recyclerView)
            adapter.openLoadAnimation()
            recyclerView.adapter = adapter
            adapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter1: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
                when (result.data[position].merchant_type_id) {
                    "2" -> MallShoppingHomeActivity.start(result.data[position].id)
                    "3" -> HotelDetailActivity.start(result.data[position].id)
                    "4" -> RestaurantDetailActivity.start(result.data[position].id)
                    "5" -> {
                    }
                    "6" -> {
                    }
                    "7" -> {
                    }
                    "8" -> {
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun getContentView(): Int {
        return R.layout.activity_my_follow
    }
}