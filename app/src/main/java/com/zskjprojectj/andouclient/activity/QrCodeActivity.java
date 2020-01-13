package com.zskjprojectj.andouclient.activity;

import android.content.SharedPreferences;
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
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.SharedPreferencesManager;

import java.io.IOException;

import butterknife.BindView;
import mlxy.utils.L;

public class QrCodeActivity extends BaseActivity {

    CaptureHelper mCaptureHelper;
    private String erweicode;

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
            //ToastUtils.showShort(result);
            HttpRxObservable.getObservable(ApiUtils.getApiService().binding(LoginInfoUtil.getUid(),LoginInfoUtil.getToken(),result)).subscribe(new BaseObserver<Object>(mAt) {
                @Override
                public void onHandleSuccess(Object o) throws IOException {

                }
            });

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
