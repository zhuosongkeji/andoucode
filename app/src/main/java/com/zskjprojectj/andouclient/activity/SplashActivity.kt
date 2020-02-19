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
import kotlinx.android.synthetic.main.activity_splash.*
import mlxy.utils.App

class SplashActivity : AppCompatActivity() {

    internal var dialog: Dialog? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        versionTxt.text = "V${AppUtils.getAppVersionName()}"
        if (SPUtils.getInstance().getBoolean(KEY_PROTOCOL_BEEN_SHOWN, false)) {
            start()
        } else {
            showProtocolDialog()
        }
    }

    private fun showProtocolDialog() {
        findViewById<View>(R.id.logo).postDelayed({ dialog = DialogUtil.showProtocolDialog(this, { v -> start() }, { v -> finish() }) },
                500)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    private fun start() {
        SPUtils.getInstance().put(KEY_PROTOCOL_BEEN_SHOWN, true)
        findViewById<View>(R.id.logo).postDelayed({
            ActivityUtils.startActivity(MainActivity::class.java)
            finish()
        }, 1000)
    }

    companion object {

        private val KEY_PROTOCOL_BEEN_SHOWN = "KEY_PROTOCOL_BEEN_SHOWN"
    }
}
