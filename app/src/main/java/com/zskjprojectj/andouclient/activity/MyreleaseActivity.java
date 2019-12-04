package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.ToastUtil;

/**
 * 我的钱包
 */
public class MyreleaseActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_myrelease);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("我的发布");
        topView.setRightTitle("我要发布");
    }

    @Override
    protected void initViews() {
       topView.setRightTextListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ToastUtil.showToast("sssssssss");
           }
       });
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
