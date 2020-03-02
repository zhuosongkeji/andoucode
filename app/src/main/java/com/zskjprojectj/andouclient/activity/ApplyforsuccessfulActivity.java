package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyforsuccessfulActivity extends BaseActivity {
    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;
    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_applyforsuccessful);
    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("申请结果");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {

    }
    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
