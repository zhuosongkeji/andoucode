package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.SelectPicAdapter
import kotlinx.android.synthetic.main.activity_tie_ba_release.*

class TieBaReleaseActivity : BaseActivity() {

    val adapter=SelectPicAdapter(6)

    override fun getContentView() = R.layout.activity_tie_ba_release

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "发布贴吧信息")
        adapter.bindToRecyclerView(recyclerView)
    }
}
