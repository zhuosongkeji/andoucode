package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.CountDownTimerUtils
import com.zskjprojectj.andouclient.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(mActivity)
        BarUtils.setStatusBarLightMode(mActivity, true)
        backBtn.setOnClickListener { onBackPressed() }
        registered_agree_xieyi_button.setOnCheckedChangeListener { _, checked ->
            registered_button.isEnabled = checked
        }
        registered_login_textview.setOnClickListener {
            onBackPressed()
        }
        getCodeBtn.setOnClickListener {
            val mobileStr = mobileEdt.text.toString()
            if (!RegexUtils.isMobileSimple(mobileStr)) {
                ToastUtils.showShort("请输入正确的手机号码!")
            } else {
                RequestUtil.request(mActivity, true, false,
                        { ApiUtils.getApiService().sendCode(mobileStr, "1") },
                        {
                            val countDownTimer = CountDownTimerUtils(getCodeBtn, 60000, 1000)
                            countDownTimer.start()
                            ToastUtils.showShort("验证码短信已发送,请注意查收!")
                        })
            }
        }
        registered_button.setOnClickListener {
            val mobileStr = mobileEdt.text.toString()
            val codeStr = codeEdt.text.toString()
            val passwordStr = passwordEdt.text.toString()
            when {
                !RegexUtils.isMobileSimple(mobileStr) ->
                    ToastUtils.showShort("请输入正确的手机号码!")
                codeStr.isEmpty() ->
                    ToastUtils.showShort("请输入正确的验证码!")
                passwordStr.isEmpty() ->
                    ToastUtils.showShort("请输入正确的密码!")
                else ->
                    RequestUtil.request(mActivity, true, false,
                            { ApiUtils.getApiService().register(mobileStr, passwordStr, codeStr) },
                            {
                                ToastUtils.showShort("注册成功,请登录!")
                                finish()
                            })
            }
        }
        yinsi_xieyi_textview.setOnClickListener {
            DialogUtil.showProtocolDialogNoBtns(mActivity)
        }
    }

    override fun getContentView() = R.layout.activity_register
}
