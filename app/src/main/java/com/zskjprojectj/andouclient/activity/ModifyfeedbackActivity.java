package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

public class ModifyfeedbackActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifyfeedback);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("意见反馈");
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
