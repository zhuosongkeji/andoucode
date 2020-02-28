package com.zskjprojectj.andouclient.activity

import android.graphics.Color
import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.utils.KEY_DATA
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
        val index = intent.getIntExtra(KEY_DATA, 0)
        navigationBar.selectTab(index)
    }

    override fun getContentView() = R.layout.activity_mall_home

    companion object {
        fun start(index: Int = 0) {
            ActivityUtils.startActivity(bundleOf(Pair(KEY_DATA, index)), MallHomeActivity::class.java)
        }
    }
}
