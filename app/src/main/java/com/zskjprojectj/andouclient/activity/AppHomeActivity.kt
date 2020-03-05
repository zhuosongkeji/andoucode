package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.next.easynavigation.view.EasyNavigationBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhuosongkj.android.library.app.BaseActivity
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.*
import com.zskjprojectj.andouclient.http.BaseObserver.REQUEST_CODE_LOGIN
import com.zskjprojectj.andouclient.utils.LogUtil
import kotlinx.android.synthetic.main.activity_app_home.*

class AppHomeActivity : BaseActivity() {

    val mNavigationBar: EasyNavigationBar get() = navigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(mActivity)
        navigationBar.titleItems(arrayOf("首页", "商家", "贴吧", "信息", "我的"))
                .normalIconItems(intArrayOf(
                        R.mipmap.home_icon_uncheck,
                        R.mipmap.merchants_icon_uncheck,
                        R.mipmap.info_icon,
                        R.mipmap.tieba_icon_uncheck,
                        R.mipmap.me_icon_uncheck))
                .selectIconItems(intArrayOf(
                        R.mipmap.home_icon_check,
                        R.mipmap.merchants_icon_check,
                        R.mipmap.info_icon,
                        R.mipmap.tieba_icon_check,
                        R.mipmap.me_icon_check))
                .fragmentList(arrayListOf<Fragment>(
                        AppHomeFragment(),
                        MerchantListFragment(),
                        TieBaFragment(),
                        MessageFragment(),
                        MeFragment()))
                .anim(null)
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(0)
                .addAlignBottom(true)
                .addAsFragment(true)
                .normalTextColor(Color.parseColor("#646464"))
                .selectTextColor(Color.parseColor("#5ED3AE"))
                .fragmentManager(supportFragmentManager)
                .canScroll(false)
                .mode(EasyNavigationBar.MODE_ADD)
                .onTabClickListener { _, position ->
                    if (position == 4) {
                        (navigationBar.fragmentList[4] as MeFragment).refresh()
                    }
                    false
                }
                .build()
        navigationBar.selectTab(0)
        checkRxPermission()
    }

    override fun onBackPressed() {
        if (baseContentView.tag == null) {
            ToastUtils.showShort("再按一次退出")
            baseContentView.tag = Any()
            baseContentView.postDelayed({ baseContentView.tag = null }, 2000)
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_CANCELED) {
            navigationBar.selectTab(0)
        }
    }

    @SuppressLint("CheckResult")
    private fun checkRxPermission() {
        val rxPermissions = RxPermissions(mActivity as Activity)
        val permissionsArr = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CALL_PHONE)
        rxPermissions.requestEach(*permissionsArr)
                .subscribe { permission ->
                    when {
                        permission.shouldShowRequestPermissionRationale -> {
                            showPermissionAlertDialog(mActivity)
                        }
                    }
                }
    }

    private fun showPermissionAlertDialog(context: Activity?) {
        if (context != null) {
            AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("权限要求")
                    .setMessage("如果没有请求的权限，此应用程序可能无法正常工作。打开app设置界面修改app权限")
                    .setPositiveButton("确定") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", context.packageName, null)
                        intent.data = uri
                        context.startActivity(intent)
                        context.finish()
                    }
                    .setNegativeButton("取消", null)
                    .create()
                    .show()
        }
    }

    override fun getContentView() = R.layout.activity_app_home
}
