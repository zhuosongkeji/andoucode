package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

public class DownloadappActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_downloadapp);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
