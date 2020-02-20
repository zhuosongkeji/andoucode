package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.utils.DialogUtil
import com.zskjprojectj.andouclient.utils.KEY_PROTOCOL_AGREED
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        versionTxt.text = "V${AppUtils.getAppVersionName()}"
        if (needShowProtocolDialog()) {
            showProtocolDialog()
        } else {
            enterHome()
        }
    }

    private fun needShowProtocolDialog(): Boolean {
        return !SPUtils.getInstance().getBoolean(KEY_PROTOCOL_AGREED, false)
    }

    private fun showProtocolDialog() {
        logo.postDelayed({
            dialog = DialogUtil.showProtocolDialog(this,
                    {
                        SPUtils.getInstance().put(KEY_PROTOCOL_AGREED, true)
                        enterHome()
                    },
                    { finish() })
        }, 500)
    }

    private fun enterHome() {
        findViewById<View>(R.id.logo).postDelayed({
            ActivityUtils.startActivity(AppHomeActivity::class.java)
            finish()
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }
}
