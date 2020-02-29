package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.framgent_pin_tuan.*

open class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()
    override fun getContentView() = R.layout.fragment_square

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener { _, _, position ->
            TieBaDetailsActivity.start(adapter.getItem(position)?.id)
        }
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().tieBaList(
                    LoginInfoUtil.getUid(),
                    null,
                    pageLoadUtil.page)
        }
    }
}
