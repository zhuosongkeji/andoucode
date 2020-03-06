package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

public class WithdrawalresultsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "提现结果");
        Button btn_gotomywallet = findViewById(R.id.btn_gotomywallet);
        btn_gotomywallet.setOnClickListener(view -> ActivityUtils.startActivity(MyWalletActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_withdrawalresults;
    }
}
