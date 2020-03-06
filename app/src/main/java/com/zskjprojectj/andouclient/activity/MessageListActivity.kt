package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.model.BaseResult
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.MessageListAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.http.MessageListResponse
import com.zskjprojectj.andouclient.model.MessageType
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LogUtil
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_message_list.*

class MessageListActivity : BaseActivity() {

    val adapter = MessageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "消息列表")
        adapter.setOnItemClickListener { _, _, position ->
            val message = adapter.getItem(position)
            RequestUtil.request(mActivity, true, false,
                    { ApiUtils.getApiService().readMessage(LoginInfoUtil.getUid(), message?.info_id.toString()) },
                    {
                        message?.read = 1
                        adapter.notifyItemChanged(position)
                    })
            TieBaDetailsActivity.start(message?.post_id.toString())
        }
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load({
            ApiUtils.getApiService().messageList(LoginInfoUtil.getUid(),
                    type_id = intent.getIntExtra(KEY_DATA, 0),
                    page = pageLoadUtil.page)
        }, { _, data ->
            if (data is MessageListResponse && adapter.controlText.isEmpty()) {
                adapter.controlText = data.list?.join_msg ?: ""
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun getContentView() = R.layout.activity_message_list

    companion object {
        fun start(type: Int) {
            ActivityUtils.startActivity(bundleOf(Pair(KEY_DATA, type)), MessageListActivity::class.java)
        }
    }
}
