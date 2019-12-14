package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 新增地址
 */
public class NewaddressActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_newaddress);
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
