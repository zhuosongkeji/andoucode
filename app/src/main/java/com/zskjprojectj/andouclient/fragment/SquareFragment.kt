package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import android.text.LoginFilter
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.entity.TieZi
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_mall_goods_details.*
import kotlinx.android.synthetic.main.activity_shopordersendetailsrefund.*
import kotlinx.android.synthetic.main.fragment_square.view.*
import kotlinx.android.synthetic.main.framgent_pin_tuan.*
import kotlinx.android.synthetic.main.item_squarefragment.view.*
import java.util.ArrayList

open class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()
    private var isCollection = true
    override fun getContentView() = R.layout.fragment_square

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    TieBaDetailsActivity.start(adapter.getItem(position))
                }
        adapter.setOnItemChildClickListener { adapter1, view, position ->
            val collected = if (isCollection) "1" else "0"
            RequestUtil.request(mActivity,true,false,
                    {ApiUtils.getApiService().tieBaLike(
                            LoginInfoUtil.getUid(),
                            adapter.getItem(position)?.id,
                            collected)},
                    {
                        if (collected == "1") {
                            view.likeIcn.setImageResource(R.mipmap.ic_zan)
                            val likeNum=adapter.getItem(position)?.vote?.toInt()?.plus(1)
                            view.likeTxt.text = likeNum.toString()
                            isCollection = false
                        } else {
                            view.likeIcn.setImageResource(R.mipmap.dianzhan)
                            val likeNum=adapter.getItem(position)?.vote?.toInt()?.minus(1)
                            view.likeTxt.text = likeNum.toString()
                            isCollection = true
                        }
                    })
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
