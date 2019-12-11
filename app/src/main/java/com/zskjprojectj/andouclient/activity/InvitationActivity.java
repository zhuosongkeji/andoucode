package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 分享有礼
 */
public class InvitationActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_invitation);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        topView.setTitle("分享有礼");
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
