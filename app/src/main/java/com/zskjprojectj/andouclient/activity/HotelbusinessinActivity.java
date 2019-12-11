package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 酒店入驻
 */
public class HotelbusinessinActivity extends BaseActivity {
    private Button btn_applicationhotelin;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelbusinessin);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("酒店商家入驻");
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
