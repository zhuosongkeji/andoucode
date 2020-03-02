package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaMessageActivity
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tempTitleContainer.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        tieBaMessageBtn.setOnClickListener {
            ActivityUtils.startActivity(TieBaMessageActivity::class.java)
        }
        loadData()
    }

    private fun loadData() {
        RequestUtil.request(mActivity, true, false,
                { ApiUtils.getApiService().messageTypeList(LoginInfoUtil.getUid()) },
                {
                    it.data?.count?.forEach { type ->
                        when (type.id) {
                            1L -> checkVisible(type.unread, systemMessageCountTxt)
                            2L -> checkVisible(type.unread, tieBaMessageCountTxt)
                        }
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun getContentView() = R.layout.fragment_message
}

fun checkVisible(unread: Int, countTxt: TextView?) {
    if (unread == 0) {
        countTxt?.visibility = View.GONE
    } else {
        countTxt?.visibility = View.VISIBLE
        countTxt?.text = unread.toString()
    }
}