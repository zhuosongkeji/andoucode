package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.RegexUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.CountDownTimerUtils
import com.zskjprojectj.andouclient.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_forget.*

class ForgetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCodeBtn.setOnClickListener(View.OnClickListener {
            val mobileStr = mobileEdt.text.toString()
            if (RegexUtils.isMobileSimple(mobileStr)) {
                ToastUtil.showToast("请输入正确的手机号码!")
                return@OnClickListener
            }
            RequestUtil.request(mActivity, true, false,
                    { ApiUtils.getApiService().sendCode(mobileStr, "0") },
                    {
                        val countDownTimer = CountDownTimerUtils(getCodeBtn, 60000, 1000)
                        countDownTimer.start()
                        ToastUtil.showToast("验证码短信已发送,请注意查收!")
                    })
        })
        registered_button.setOnClickListener {
            val mobileStr = mobileEdt.text.toString().trim { it <= ' ' }
            val codeStr = codeEdt.text.toString().trim { it <= ' ' }
            val passwordStr = passwordEdt.text.toString()
            when {
                mobileStr.isEmpty() ->
                    ToastUtil.showToast("请输入正确的手机号码!")
                codeStr.isEmpty() ->
                    ToastUtil.showToast("请输入正确的验证码!")
                passwordStr.isEmpty() ->
                    ToastUtil.showToast("请输入正确的密码!")
                else ->
                    RequestUtil.request(mActivity, true, false,
                            {
                                ApiUtils.getApiService().forgetPassword(
                                        mobileStr,
                                        passwordStr,
                                        codeStr
                                )
                            },
                            {
                                ToastUtil.showToast("修改成功,请登录!")
                                finish()
                            })
            }
        }
    }

    override fun getContentView() = R.layout.activity_forget
}
