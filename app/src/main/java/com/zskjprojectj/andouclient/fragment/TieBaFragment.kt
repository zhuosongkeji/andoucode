package com.zskjprojectj.andouclient.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
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
                arrayListOf(SquareFragment(), MyTieBaFragment()))
        view.commentDetails.setOnClickListener {
            ActivityUtils.startActivityForResult(
                    mFragment,TieBaReleaseActivity::class.java,666)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun getContentView() = R.layout.fragment_tiebapage


}
