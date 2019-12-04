package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 我的发布
 */
public class MywalletActivity extends BaseActivity {
    private Button balanceofprepaid;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mywallet);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("我的钱包");
    }

    @Override
    protected void initViews() {
        balanceofprepaid=findViewById(R.id.btn_balanceofprepaid);
        //设置点击事件
        balanceofprepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(BalanceofprepaidActivity.class);
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
