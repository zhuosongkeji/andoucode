package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.hotel.HotelOrderListFragment
import kotlinx.android.synthetic.main.activity_hotel_order_list.*

class HotelOrderListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "全部订单")
        viewPager.offscreenPageLimit = 4
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> ActionBarUtil.setTitle(mActivity, "全部订单")
                    1 -> ActionBarUtil.setTitle(mActivity, "待入住")
                    2 -> ActionBarUtil.setTitle(mActivity, "待评价")
                    3 -> ActionBarUtil.setTitle(mActivity, "已取消")
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        tabLayout.setViewPager(
                viewPager,
                arrayOf("全部订单", "待入住", "待评价", "已取消"),
                mActivity,
                arrayListOf(
                        HotelOrderListFragment(""),
                        HotelOrderListFragment("20"),
                        HotelOrderListFragment("40"),
                        HotelOrderListFragment("0")
                ))
        val flag = intent.getStringExtra("flag")
        if ("MallPaySuccess" == flag) {
            viewPager.setCurrentItem(1, true)
        }
    }

    override fun getContentView() = R.layout.activity_hotel_order_list
}