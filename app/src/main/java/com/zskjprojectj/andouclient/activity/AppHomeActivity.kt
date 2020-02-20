package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.blankj.utilcode.util.BarUtils
import com.next.easynavigation.view.EasyNavigationBar
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhuosongkj.android.library.app.BaseActivity
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.fragment.HomePageFragment
import com.zskjprojectj.andouclient.fragment.InfoPageFragment
import com.zskjprojectj.andouclient.fragment.MePageFragment
import com.zskjprojectj.andouclient.fragment.MerchantsPageFragment
import com.zskjprojectj.andouclient.fragment.TieBaFragment
import com.zskjprojectj.andouclient.utils.LogUtil
import com.zskjprojectj.andouclient.utils.LoginInfoUtil

import java.util.ArrayList

import io.reactivex.functions.Consumer

import com.zskjprojectj.andouclient.http.BaseObserver.REQUEST_CODE_LOGIN
import kotlinx.android.synthetic.main.activity_main.*

class AppHomeActivity : BaseActivity() {
    //定义字体颜色
    private val normalTextColor = Color.parseColor("#646464")
    private val selectTextColor = Color.parseColor("#5ED3AE")
    private val tabText = arrayOf("首页", "商家", "贴吧", "信息", "我的")
    //未选中icon
    private val normalIcon = intArrayOf(R.mipmap.home_icon_uncheck, R.mipmap.merchants_icon_uncheck, R.mipmap.info_icon, R.mipmap.tieba_icon_uncheck, R.mipmap.me_icon_uncheck)
    //选中时icon
    private val selectIcon = intArrayOf(R.mipmap.home_icon_check, R.mipmap.merchants_icon_check, R.mipmap.info_icon, R.mipmap.tieba_icon_check, R.mipmap.me_icon_check)

    private var firstTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(mActivity)
        initViews()
    }

    private fun initViews() {
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(arrayListOf(HomePageFragment(),
                        MerchantsPageFragment(),
                        TieBaFragment(),
                        InfoPageFragment(),
                        MePageFragment()))
                .anim(null)
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(0)
                .addAlignBottom(true)
                .addAsFragment(true)
                .normalTextColor(normalTextColor)
                .selectTextColor(selectTextColor)
                .fragmentManager(supportFragmentManager)
                .canScroll(false)
                .mode(EasyNavigationBar.MODE_ADD)
                .onTabClickListener(EasyNavigationBar.OnTabClickListener { view, position ->
                    if (position == 4) {
                        if (TextUtils.isEmpty(LoginInfoUtil.getToken())) {
                            LoginActivity.start(mActivity)
                            return@OnTabClickListener true
                        }
                        val mePageFragment = navigationBar.fragmentList[4] as MePageFragment
                        mePageFragment.refresh()
                    }
                    //                        switch (position) {
                    //                            case 0:
                    //                                ActionBarUtil.setVisible(mActivity, false);
                    //                                break;
                    //                            case 1:
                    //                                ActionBarUtil.setTitle(mActivity, "商家");
                    //                                break;
                    //                            case 2:
                    //                                ActionBarUtil.setVisible(mActivity, false);
                    //                                break;
                    //                            case 3:
                    //                                ActionBarUtil.setTitle(mActivity, "信息");
                    //                                break;
                    //                            case 4:
                    //                                ActionBarUtil.setVisible(mActivity,false);
                    //                                break;
                    //
                    //                        }
                    false
                })
                .build()
        navigationBar!!.selectTab(0)
        checkRxPermission()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val secondTime = System.currentTimeMillis()

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0)
            } else {
                Toast.makeText(applicationContext, "再按一次返回键退出", Toast.LENGTH_SHORT).show()
                firstTime = System.currentTimeMillis()
            }

            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_CANCELED) {
            navigationBar!!.selectTab(0)
        }
    }

    //动态申请权限
    @SuppressLint("CheckResult")
    private fun checkRxPermission() {
        val rxPermissions = RxPermissions(mActivity as Activity)
        val permissionsArr = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CALL_PHONE)
        rxPermissions
                .requestEach(
                        *permissionsArr)
                .subscribe { permission ->
                    if (permission.granted) {
                        LogUtil.i("testRxPermission CallBack onPermissionsGranted() : " + permission.name +
                                " request granted , to do something...")
                        //todo somthing

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        LogUtil.e("testRxPermission CallBack onPermissionsDenied() : " + permission.name + "request denied")
                        //ToastUtil.showShort(instance, "拒绝权限，等待下次询问哦");
                        alertDialog(mActivity)
                        //todo request permission again
                    } else {
                        LogUtil.e("testRxPermission CallBack onPermissionsDenied() : this " + permission.name + " is denied " +
                                "and never ask again")
                        // ToastUtil.showShort(instance, "拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限");
                        //todo nothing
                    }
                }
    }

    /**
     * 用户拒绝，并且选择不再提示,可以引导用户进入权限设置界面开启权限
     * 弹窗是否显示根据需求选择调用
     *
     * @param context
     */
    private fun alertDialog(context: Activity?) {
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

    override fun getContentView(): Int {
        return R.layout.activity_main
    }
}
