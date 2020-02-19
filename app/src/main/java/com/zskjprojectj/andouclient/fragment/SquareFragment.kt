package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseFragment
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.entity.TieZi
import kotlinx.android.synthetic.main.fragment_square.view.*
import java.util.ArrayList

open class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()

    override fun getContentView() = R.layout.fragment_square

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.bindToRecyclerView(view.recyclerView)
        adapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { _, _, position ->
                    TieBaDetailsActivity.start(adapter.getItem(position))
                }
        onBind(TieZi.testData)
    }

    private fun onBind(data: ArrayList<TieZi>) {
        adapter.setNewData(data)
    }
}
