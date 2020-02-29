package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import android.text.LoginFilter
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.entity.TieZi
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.fragment_square.view.*
import kotlinx.android.synthetic.main.framgent_pin_tuan.*
import java.util.ArrayList

open class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()

    override fun getContentView() = R.layout.fragment_square

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    TieBaDetailsActivity.start(adapter.getItem(position))
                }
//        onBind(TieZi.testData)
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().tieBaList(
                    LoginInfoUtil.getUid(),
                    null,
                    pageLoadUtil.page)
        }
    }

//    private fun onBind(data: ArrayList<TieZi>) {
//        adapter.setNewData(data)
//    }
}
