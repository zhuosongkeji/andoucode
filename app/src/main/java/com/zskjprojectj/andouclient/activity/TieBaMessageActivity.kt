package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import kotlinx.android.synthetic.main.activity_tie_ba_message.*

class TieBaMessageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "贴吧消息")
        likeMessageBtn.setOnClickListener {
            MessageListActivity.start(0)
        }
        commentMessageBtn.setOnClickListener {
            MessageListActivity.start(0)
        }
    }

    override fun getContentView() = R.layout.activity_tie_ba_message
}
