package com.zskjprojectj.andouclient.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.MessageListAdapter
import com.zskjprojectj.andouclient.model.Message
import com.zskjprojectj.andouclient.utils.KEY_DATA
import kotlinx.android.synthetic.main.activity_message_list.*

class MessageListActivity : BaseActivity() {

    val adapter = MessageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "消息列表")
        adapter.bindToRecyclerView(recyclerView)
        adapter.setOnItemClickListener { _, view, position ->
            TieBaDetailsActivity.start("1")
        }
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
        adapter.addData(Message("", "", "", ""))
    }

    override fun getContentView() = R.layout.activity_message_list

    companion object {
        fun start(type: Int) {
            ActivityUtils.startActivity(bundleOf(Pair(KEY_DATA, type)), MessageListActivity::class.java)
        }
    }
}
