package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.checkVisible
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_tie_ba_message.*

class TieBaMessageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "贴吧消息")
        likeMessageBtn.setOnClickListener {
            MessageListActivity.start(0)
        }
        commentMessageBtn.setOnClickListener {
            MessageListActivity.start(3)
        }
        commentMessageBtn.setOnClickListener {
            MessageListActivity.start(4)
        }
        replyMessageBtn.setOnClickListener {
            MessageListActivity.start(5)
        }
        loadData()
    }

    private fun loadData() {
        RequestUtil.request(mActivity, true, false,
                { ApiUtils.getApiService().messageTypeList(LoginInfoUtil.getUid(), 2, 2) },
                {
                    it.data?.count?.forEach { type ->
                        when (type.id) {
                            3L -> checkVisible(type.unread, likeMessageCountTxt)
                            4L -> checkVisible(type.unread, commentMessageCountTxt)
                            5L -> checkVisible(type.unread, replyMessageCountTxt)
                        }
                    }
                })
    }


    override fun getContentView() = R.layout.activity_tie_ba_message
}
