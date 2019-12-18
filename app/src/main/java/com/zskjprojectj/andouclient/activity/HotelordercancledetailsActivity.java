package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 酒店订单取消详情界面
 */
public class HotelordercancledetailsActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelordercancledetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("订单详情");
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
