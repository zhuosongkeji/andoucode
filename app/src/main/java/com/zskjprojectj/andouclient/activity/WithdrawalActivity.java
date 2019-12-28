package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

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
        btn_surewithdrawal = findViewById(R.id.btn_surewithdrawal);
        btn_surewithdrawal.setOnClickListener(view -> {
            EditText amountEdt = findViewById(R.id.amountEdt);
            float amount = Float.valueOf(amountEdt.getText().toString());
            if (amount <= 0) {
                ToastUtil.showToast("提现金额必须大于0！");
                return;
            }
            HttpRxObservable.getObservable(ApiUtils.getApiService().cash(
                    LoginInfoUtil.getUid(),
                    LoginInfoUtil.getToken(),
                    amount,
                    "13888888888",
                    "6222031300255596882"
            )).subscribe(new BaseObserver<Object>(mAt) {
                @Override
                public void onHandleSuccess(Object o) throws IOException {
                    jumpActivity(WithdrawalresultsActivity.class);
                }
            });
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
