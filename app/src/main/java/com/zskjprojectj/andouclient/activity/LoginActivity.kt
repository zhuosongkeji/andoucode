package com.zskjprojectj.andouclient.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.event.LoginSuccessEvent
import com.zskjprojectj.andouclient.event.WeiXinLoginEvent
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.KEY_FOR_RESULT
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.REQUEST_CODE_LOGIN
import com.zskjprojectj.andouclient.utils.WEI_XIN_APP_ID
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        BarUtils.transparentStatusBar(mActivity)
        BarUtils.setStatusBarLightMode(mActivity, true)
        backBtn.setOnClickListener { onBackPressed() }
        btn_login.setOnClickListener {
            val account = registered_phonenum.text.toString()
            val password = et_loginpwd.text.toString()
            when {
                account.isEmpty() -> {
                    ToastUtils.showShort("请输入正确的手机号码")
                }
                password.isEmpty() -> {
                    ToastUtils.showShort("密码不能为空")
                }
                else -> {
                    RequestUtil.request(mActivity, true, false,
                            { ApiUtils.getApiService().login(account, password) },
                            {
                                LoginInfoUtil.saveLoginInfo(it.data.id, it.data.token)
                                setResult(Activity.RESULT_OK)
                                EventBus.getDefault().post(LoginSuccessEvent())
                                if (!intent.getBooleanExtra(KEY_FOR_RESULT, false)) {
                                    ActivityUtils.startActivity(AppHomeActivity::class.java)
                                }
                                finish()
                            })
                }
            }

        }
        btnNewregistered.setOnClickListener {
            ActivityUtils.startActivity(RegisterActivity::class.java)
        }
        forgetPasswordBtn.setOnClickListener {
            ActivityUtils.startActivity(ForgetActivity::class.java)
        }
        img_weixinlogin.setOnClickListener(View.OnClickListener {
            val api = WXAPIFactory.createWXAPI(this@LoginActivity, WEI_XIN_APP_ID, true)
            if (!api.isWXAppInstalled) {
                ToastUtils.showShort("您手机尚未安装微信，请安装后再登录")
                return@OnClickListener
            }
            api.registerApp(WEI_XIN_APP_ID)
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "wechat_sdk_jj_login_state"
            api.sendReq(req)
        })
    }

    @Subscribe
    fun onWeiXinLogin(event: WeiXinLoginEvent) {
        RequestUtil.request(mActivity, true, false,
                { ApiUtils.getApiService().loginweixin(event.code) },
                {
                    LoginInfoUtil.saveLoginInfo(it.data.id, it.data.token)
                    if (it.data.token.isNotEmpty()) {
                        if (!intent.getBooleanExtra(KEY_FOR_RESULT, false)) {
                            ActivityUtils.startActivity(AppHomeActivity::class.java)
                        }
                        EventBus.getDefault().post(LoginSuccessEvent())
                        this@LoginActivity.setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        if (it.data == null) {
                            ToastUtils.showShort("无微信登录信息!")
                        } else {
                            WeiXinBindPhoneActivity.start(mActivity, it.data.name, it.data.avator, it.data.openid)
                        }
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 666) {
            setResult(Activity.RESULT_OK)
            EventBus.getDefault().post(LoginSuccessEvent())
            if (!intent.getBooleanExtra(KEY_FOR_RESULT, false)) {
                ActivityUtils.startActivity(mActivity, AppHomeActivity::class.java)
            }
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        started = false
        EventBus.getDefault().unregister(this)
    }

    override fun getContentView() = R.layout.activity_login

    companion object {
        private var started = false

        fun start(activity: Activity, requestCode: Int = REQUEST_CODE_LOGIN) {
            if (started) return
            started = true
            val bundle = Bundle()
            bundle.putBoolean(KEY_FOR_RESULT, true)
            ActivityUtils.startActivityForResult(bundle, activity, LoginActivity::class.java, requestCode)
        }

        fun start(fragment: Fragment, requestCode: Int) {
            if (started) return
            started = true
            val bundle = Bundle()
            bundle.putBoolean(KEY_FOR_RESULT, true)
            ActivityUtils.startActivityForResult(bundle, fragment, LoginActivity::class.java, requestCode)
        }
    }
}