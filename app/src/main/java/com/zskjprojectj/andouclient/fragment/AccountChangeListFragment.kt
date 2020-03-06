package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.AccountChangeListAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.fragment_account_change_list.*

class AccountChangeListFragment(val type: Int = 0) : BaseFragment() {
    var adapter = AccountChangeListAdapter()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load {
            if (type == 0) {
                return@load ApiUtils.getApiService().balanceDetail(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        pageLoadUtil.page
                )
            } else {
                return@load ApiUtils.getApiService().cashDetail(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        pageLoadUtil.page)
            }
        }
    }

    override fun getContentView() = R.layout.fragment_account_change_list
}