package com.zskjprojectj.andouclient.activity

import android.graphics.Color
import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.MallCategoryFragment
import com.zskjprojectj.andouclient.fragment.MallShoppingFragment
import com.zskjprojectj.andouclient.fragment.mall.MallHomepageFragment1
import kotlinx.android.synthetic.main.activity_mall_home.*

class MallHomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationBar.titleItems(arrayOf("首页", "分类", "购物车"))
                .normalIconItems(intArrayOf(
                        R.mipmap.home_icon_uncheck,
                        R.mipmap.classification_icon_uncheck,
                        R.mipmap.shopping_cart_uncheck))
                .selectIconItems(intArrayOf(
                        R.mipmap.home_icon_check,
                        R.mipmap.classification_icon_check,
                        R.mipmap.shopping_cart_check))
                .fragmentList(arrayListOf(
                        MallHomepageFragment1(),
                        MallCategoryFragment(),
                        MallShoppingFragment()))
                .normalTextColor(Color.parseColor("#646464"))
                .selectTextColor(Color.parseColor("#5ED3AE"))
                .fragmentManager(supportFragmentManager)
                .canScroll(true)
                .onTabClickListener { _, position ->
                    when (position) {
                        0 -> ActionBarUtil.setVisible(mActivity, false)
                        1 -> ActionBarUtil.setTitle(mActivity, "商品分类")
                        2 -> ActionBarUtil.setTitle(mActivity, "购物车")
                    }
                    false
                }
                .build()
        navigationBar.selectTab(0)
        val id = intent.getStringExtra("id")
        if ("MallShopping" == id) {
            navigationBar.selectTab(2)
        }
        val tag = intent.getStringExtra("tag")
        if ("backHome" == tag) {
            navigationBar.selectTab(0)
        }
    }

    override fun getContentView() = R.layout.activity_mall_home
}
