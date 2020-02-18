package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseFragment
import com.zskjprojectj.andouclient.R
import kotlinx.android.synthetic.main.fragment_tiebapage.*
import kotlinx.android.synthetic.main.fragment_tiebapage.view.*

class TieBaFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.tabLayout.setViewPager(
                viewPager,
                arrayOf("广场", "我的"),
                activity,
                arrayListOf(SquareFragment(), CircleoffriendsFragment()))
    }

    override fun getContentView() = R.layout.fragment_tiebapage
}
