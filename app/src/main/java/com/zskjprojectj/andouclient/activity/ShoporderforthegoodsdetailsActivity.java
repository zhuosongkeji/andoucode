package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 待收货商品详情
 */
public class ShoporderforthegoodsdetailsActivity extends BaseActivity {
    private Button btn_checkthelogistics,btn_getgoods;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_shoporderforthegoodsdetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("商城订单");
    }

    @Override
    protected void initViews() {
        btn_checkthelogistics=findViewById(R.id.btn_checkthelogistics);
        btn_checkthelogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(CheckthelogisticsActivity.class);
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
