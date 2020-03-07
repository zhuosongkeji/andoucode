package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_withdrawal.*

class WithdrawalActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "余额提现")
        allBtn.setOnClickListener {
            amountEdt.setText(accountTxt.text.toString())
        }
        btn_surewithdrawal.setOnClickListener {
            val amount = amountEdt.text.toString().toFloatOrNull()
            if (amount == null || amount <= 0) {
                ToastUtils.showShort("请输入正确的提现金额")
            } else if (!RegexUtils.isMobileSimple(phoneEdt.text.toString())) {
                ToastUtils.showShort("请输入正确的手机号")
            } else if (accountEdt.text.toString().isEmpty()) {
                ToastUtils.showShort("清输入正确的银行账号")
            } else {
                RequestUtil.request(mActivity, true, false, {
                    ApiUtils.getApiService().cash(
                            LoginInfoUtil.getUid(),
                            LoginInfoUtil.getToken(),
                            amount,
                            phoneEdt.text.toString(),
                            accountEdt.text.toString()
                    )
                }, {
                    ActivityUtils.startActivity(WithdrawalresultsActivity::class.java)
                    finish()
                })
            }
        }
        RequestUtil.request(mActivity, true, true,
                {
                    ApiUtils.getApiService().cashDetail(
                            LoginInfoUtil.getUid(),
                            LoginInfoUtil.getToken(),
                            1
                    )
                },
                {
                    accountTxt.text = it.data.money
                })
    }

    override fun getContentView() = R.layout.activity_withdrawal
}