package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.king.zxing.CaptureHelper
import com.king.zxing.ViewfinderView
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil


class QrCodeActivity : BaseActivity() {
    var mCaptureHelper: CaptureHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "扫一扫")
        val surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
        val viewfinderView: ViewfinderView = findViewById(R.id.viewfinderView)
        val ivTorch = findViewById<View>(R.id.ivTorch)
        mCaptureHelper = CaptureHelper(this, surfaceView, viewfinderView, ivTorch)
        mCaptureHelper?.setOnCaptureCallback { result: String ->
            RequestUtil.request(mActivity, true, false, {
                ApiUtils.getApiService().binding(LoginInfoUtil.getUid(), LoginInfoUtil.getToken(), result)
            }, {
                ToastUtils.showShort(it.msg)
                finish()
            })
            true
        }
        mCaptureHelper?.onCreate()
        mCaptureHelper?.vibrate(true)
                ?.fullScreenScan(true) //全屏扫码
                ?.continuousScan(false)
    }

    override fun onResume() {
        super.onResume()
        mCaptureHelper?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mCaptureHelper?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCaptureHelper?.onDestroy()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mCaptureHelper?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun getContentView(): Int {
        return R.layout.activity_qr_code
    }
}