package com.zskjprojectj.andouclient.fragment

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.zhuosongkj.android.library.app.BaseFragment
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaReleaseActivity
import kotlinx.android.synthetic.main.fragment_tiebapage.*
import kotlinx.android.synthetic.main.fragment_tiebapage.view.*

class TieBaFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.tabLayout.setViewPager(
                viewPager,
                arrayOf("广场", "我的"),
                activity,
                arrayListOf(TieBaListFragment(), MyTieBaFragment()))
        view.releaseBtn.setOnClickListener {
            ActivityUtils.startActivityForResult(
                    mFragment, TieBaReleaseActivity::class.java, 666)
        }
        title_view.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
    }

    override fun getContentView() = R.layout.fragment_tiebapage
}
