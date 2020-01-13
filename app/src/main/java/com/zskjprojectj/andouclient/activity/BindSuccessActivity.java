package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class BindSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_bindsuccess);
    }

    @Override
    protected void initViews() {
        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("分享成功");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @OnClick({R.id.iv_header_back, R.id.btn_jumpmain})
    public void clickBack(View view)
    {
        switch (view.getId())
        {
            case  R.id.iv_header_back:
                finish();
                break;
            case R.id.btn_jumpmain:
                jumpActivity(MainActivity.class);
                break;
        }
    }
}
