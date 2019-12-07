package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 饭店下单结果
 */
public class OrdersuccessfullyActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_ordersuccessfully);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("下单成功");
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
