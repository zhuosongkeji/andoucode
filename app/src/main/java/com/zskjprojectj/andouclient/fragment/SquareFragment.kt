package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseFragment
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.entity.Square
import com.zskjprojectj.andouclient.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_square.view.*
import java.util.ArrayList

class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()

    override fun getContentView() = R.layout.fragment_square

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.bindToRecyclerView(view.recyclerView)
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position -> ToastUtil.showToast("信息功能暂未完善给您带来的不便敬请谅解") }
        onBind(Square.testData)

    }

    private fun onBind(data: ArrayList<Square>) {
        adapter.setNewData(data)
    }
}
