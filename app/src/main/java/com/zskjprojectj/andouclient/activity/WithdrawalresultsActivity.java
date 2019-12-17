package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

public class WithdrawalresultsActivity extends BaseActivity {
    private Button btn_gotomywallet;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_withdrawalresults);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("提现结果");
    }

    @Override
    protected void initViews() {
        btn_gotomywallet=findViewById(R.id.btn_gotomywallet);
        btn_gotomywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(MywalletActivity.class);
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
