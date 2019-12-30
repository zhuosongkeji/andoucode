package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.king.zxing.CaptureHelper;
import com.king.zxing.ViewfinderView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;

public class QrCodeActivity extends BaseActivity {

    CaptureHelper mCaptureHelper;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_qr_code);
    }

    @Override
    protected void initViews() {
        setTitle("扫一扫");
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        ViewfinderView viewfinderView = findViewById(R.id.viewfinderView);
        View ivTorch = findViewById(R.id.ivTorch);
        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, ivTorch);
        mCaptureHelper.setOnCaptureCallback(result -> {
            ToastUtils.showShort(result);
            return true;
        });
        mCaptureHelper.onCreate();
        mCaptureHelper.vibrate(true)
                .fullScreenScan(true)//全屏扫码
                .continuousScan(false);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCaptureHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCaptureHelper.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
