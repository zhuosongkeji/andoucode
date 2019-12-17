package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 评价界面
 */
public class ToevaluateActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_toevaluate);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("发表评论");
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
