package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.GuideAdapter
import com.zskjprojectj.andouclient.utils.DialogUtil
import com.zskjprojectj.andouclient.utils.KEY_NEED_SHOW_GUIDE
import com.zskjprojectj.andouclient.utils.KEY_PROTOCOL_AGREED
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
        if (needShowProtocolDialog()) {
            showProtocolDialog()
        } else {
            enterHome(1000)
        }
    }

    private fun needShowGuide(): Boolean {
        return SPUtils.getInstance().getBoolean(KEY_NEED_SHOW_GUIDE, true)
    }

    private fun needShowProtocolDialog(): Boolean {
        return !SPUtils.getInstance().getBoolean(KEY_PROTOCOL_AGREED, false)
    }

    private fun showProtocolDialog() {
        logo.postDelayed({
            DialogUtil.showProtocolDialog(this,
                    {
                        SPUtils.getInstance().put(KEY_PROTOCOL_AGREED, true)
                        if (needShowGuide()) {
                            showGuide()
                        } else {
                            enterHome(1000)
                        }
                    },
                    { finish() })
        }, 500)
    }

    private fun showGuide() {
        SPUtils.getInstance().put(KEY_NEED_SHOW_GUIDE, false)
        guideViewPager.visibility = View.VISIBLE
        val guideAdapter = GuideAdapter(supportFragmentManager)
        guideAdapter.onStartClickListener = View.OnClickListener {
            enterHome(0)
        }
        guideViewPager.adapter = guideAdapter
    }

    private fun enterHome(delay: Long) {
        findViewById<View>(R.id.logo).postDelayed({
            ActivityUtils.startActivity(AppHomeActivity::class.java)
            finish()
        }, delay)
    }
}
