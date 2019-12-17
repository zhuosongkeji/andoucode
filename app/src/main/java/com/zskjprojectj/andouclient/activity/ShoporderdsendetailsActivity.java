package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 待发货订单详情页面
 */
public class ShoporderdsendetailsActivity extends BaseActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_shoporderdsendetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("商城订单");
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
