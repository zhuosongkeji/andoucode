package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 关于我们
 */
public class ModifyaboutusActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifyaboutus);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("关于我们");
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
