package com.zskjprojectj.andouclient.activity

import android.graphics.Color
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.MallCategoryFragment
import com.zskjprojectj.andouclient.fragment.MallCartFragment
import com.zskjprojectj.andouclient.fragment.mall.MallHomeFragment
import com.zskjprojectj.andouclient.utils.KEY_DATA
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
                .fragmentList(arrayListOf<Fragment>(
                        MallHomeFragment(),
                        MallCategoryFragment(),
                        MallCartFragment()))
                .normalTextColor(Color.parseColor("#646464"))
                .selectTextColor(Color.parseColor("#5ED3AE"))
                .fragmentManager(supportFragmentManager)
                .canScroll(true)
                .onTabClickListener { _, position ->
                    changeActionBarTitle(position)
                    false
                }
                .build()
        val index = intent.getIntExtra(KEY_DATA, 0)
        changeActionBarTitle(index)
        navigationBar.selectTab(index)
    }

    private fun changeActionBarTitle(index: Int) {
        when (index) {
            0 -> ActionBarUtil.setVisible(mActivity, false)
            1 -> ActionBarUtil.setTitle(mActivity, "商品分类")
            2 -> ActionBarUtil.setTitle(mActivity, "购物车")
        }
    }

    override fun getContentView() = R.layout.activity_mall_home

    companion object {
        fun start(index: Int = 0) {
            ActivityUtils.startActivity(bundleOf(Pair(KEY_DATA, index)), MallHomeActivity::class.java)
        }
    }
}
