package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.MeShopFragment
import kotlinx.android.synthetic.main.activity_mall_order_list.*

class MallOrderListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "全部订单")
        tabLayout.setViewPager(viewPager,
                arrayOf("全部订单", "待付款", "待发货", "待收货", "待评价"),
                mActivity,
                arrayListOf(MeShopFragment(""),
                        MeShopFragment("10"),
                        MeShopFragment("20"),
                        MeShopFragment("40"),
                        MeShopFragment("50")))
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> ActionBarUtil.setTitle(mActivity, "全部订单")
                    1 -> ActionBarUtil.setTitle(mActivity, "待付款")
                    2 -> ActionBarUtil.setTitle(mActivity, "待发货")
                    3 -> ActionBarUtil.setTitle(mActivity, "待收货")
                    4 -> ActionBarUtil.setTitle(mActivity, "待评价")
                }
            }

            override fun onTabReselect(position: Int) {
            }
        })
        val flag = intent.getStringExtra("flag")
        if ("MallPaySuccess" == flag) {
            tabLayout.setCurrentTab(2, true)
        }
    }

    override fun getContentView() = R.layout.activity_mall_order_list
}