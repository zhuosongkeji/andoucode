package com.zskjprojectj.andouclient.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.LoginActivity
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.fragment_circleoffriends.view.*


class MyTieBaFragment : SquareFragment() {
    override fun getContentView() = R.layout.fragment_circleoffriends

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.loginBtn.setOnClickListener {
            LoginActivity.start(mFragment, 666)
        }
        if (LoginInfoUtil.getToken().isNotEmpty()) {
            view.loginBtn.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 666) {
            //登录成功后刷新
            view.loginBtn.visibility = View.GONE
        }
    }
}
