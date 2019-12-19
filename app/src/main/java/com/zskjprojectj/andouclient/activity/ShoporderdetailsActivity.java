package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

public class ShoporderdetailsActivity extends BaseActivity {
    private Button btn_gotopaymentdetail;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_shoporderdetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("商城订单");
    }

    @Override
    protected void initViews() {
        btn_gotopaymentdetail=findViewById(R.id.btn_gotopaymentdetail);
        btn_gotopaymentdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(MallOnlineOrderActivity.class);
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
