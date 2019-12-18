package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 酒店订单去评价界面
 */
public class HotelordergotoevaluationActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelordergotoevaluation);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("发布评论");
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
