package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class DownloadappActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    @OnClick(R.id.iv_header_back)
    public void clickView(){
        finish();
    }
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_downloadapp);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        mHeaderTitle.setText("下载APP");
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
