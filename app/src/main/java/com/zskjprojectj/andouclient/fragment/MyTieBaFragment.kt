package com.zskjprojectj.andouclient.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.LoginActivity
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.fragment_my_tieba.view.*

class MyTieBaFragment : TieBaListFragment() {
    override fun getContentView() = R.layout.fragment_my_tieba

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view.loginBtn.setOnClickListener {
            LoginActivity.start(mFragment, 666)
        }
    }

    override fun loadData() {
        if (LoginInfoUtil.isLogin()) {
            view.loginBtn.visibility = View.GONE
            super.loadData()
        } else {
            view.loginBtn.visibility = View.VISIBLE
        }
    }

    override fun getType() = "mine"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 666) {
            loadData()
        }
    }
}
