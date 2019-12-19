package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 待发货订单详情页面
 */
public class ShoporderdsendetailsActivity extends BaseActivity {
    private Button btn_shopordersendetailsrefund;
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
        btn_shopordersendetailsrefund=findViewById(R.id.btn_shopordersendetailsrefund);
        btn_shopordersendetailsrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ShopordersendetailsrefundActivity.class);
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
