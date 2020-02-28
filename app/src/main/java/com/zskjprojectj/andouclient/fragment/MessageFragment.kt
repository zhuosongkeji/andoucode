package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.zhuosongkj.android.library.app.BaseFragment
import com.zskjprojectj.andouclient.R
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tempTitleContainer.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
    }

    override fun getContentView() = R.layout.fragment_message
}