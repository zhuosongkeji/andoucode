package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 商城商家入驻
 */
public class MallmerchantsbusinessinActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mallmerchantsbusinessin);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("商城商家入驻");
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
