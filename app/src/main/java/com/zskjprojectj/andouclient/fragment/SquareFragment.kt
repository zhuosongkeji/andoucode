package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.framgent_pin_tuan.*

open class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()
    private var isCollection = true
    override fun getContentView() = R.layout.fragment_square

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener { _, _, position ->
            TieBaDetailsActivity.start(adapter.getItem(position)?.id)
        }
        adapter.setOnItemChildClickListener { _, _, position ->
            val collected = if (isCollection) "1" else "0"
            val tieBa = adapter.getItem(position)
            RequestUtil.request(mActivity, true, false,
                    {
                        ApiUtils.getApiService().tieBaLike(
                                LoginInfoUtil.getUid(),
                                adapter.getItem(position)?.id,
                                collected)
                    },
                    {
                        RequestUtil.request(mActivity, true, false, {
                            ApiUtils.getApiService().tieBaDetail(tieBa?.id, 1)
                        }, {
                            tieBa?.vote = it.data.vote
                            adapter.notifyDataSetChanged()
                        })
                    })
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
