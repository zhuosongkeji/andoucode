package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;


/**
 *余额充值
 */
public class BalanceofprepaidActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_balanceofprepaid);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("余额充值");
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
