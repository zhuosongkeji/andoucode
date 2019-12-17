package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 提现
 */
public class WithdrawalActivity extends BaseActivity {
    private Button btn_surewithdrawal;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_withdrawal);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("余额提现");
    }

    @Override
    protected void initViews() {
        btn_surewithdrawal=findViewById(R.id.btn_surewithdrawal);
        btn_surewithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(WithdrawalresultsActivity.class);
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
