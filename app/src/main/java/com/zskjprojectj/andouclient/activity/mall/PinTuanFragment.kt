package com.zskjprojectj.andouclient.activity.mall

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.mall.PinTuanGoodsAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.PinTuan.PinTuanType
import kotlinx.android.synthetic.main.framgent_pin_tuan.*

class PinTuanFragment(private val pinTuanType: PinTuanType) : BaseFragment() {
    var adapter = PinTuanGoodsAdapter()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.onItemChildClickListener =
                BaseQuickAdapter.OnItemChildClickListener { _, _, position: Int ->
                    MallGoodsDetailsActivity.start(adapter.getItem(position)?.goods_id)
                }
        val pagetUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pagetUtil.load {
            ApiUtils.getApiService().pinTuanGoods(pinTuanType.id, pagetUtil.page)
        }
    }

    override fun getContentView() = R.layout.framgent_pin_tuan
}