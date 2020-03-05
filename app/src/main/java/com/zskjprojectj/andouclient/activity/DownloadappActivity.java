package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

public class DownloadappActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(mActivity, Color.parseColor("#F54740"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_downloadapp;
    }
}
