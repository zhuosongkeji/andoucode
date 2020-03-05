package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.TieBaListAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.http.PostSuccessEvent
import com.zskjprojectj.andouclient.model.TieBa
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.framgent_pin_tuan.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


open class TieBaListFragment : BaseFragment() {
    lateinit var pageLoadUtil: PageLoadUtil<TieBa>
    val adapter = TieBaListAdapter()
    override fun getContentView() = R.layout.fragment_tieba_list
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EventBus.getDefault().register(this)
        adapter.setOnItemClickListener { _, _, position ->
            TieBaDetailsActivity.start(adapter.getItem(position)?.id)
        }
        pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        loadData()
    }

    protected open fun loadData() {
        pageLoadUtil.load {
            ApiUtils.getApiService().tieBaList(
                    LoginInfoUtil.getUid(),
                    getType(),
                    pageLoadUtil.page)
        }
    }

    open fun getType(): String = "public"

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPostSuccessEventReceive(event: PostSuccessEvent) {
        pageLoadUtil.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
