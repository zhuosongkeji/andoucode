package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

public class ApplyforsuccessfulActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity,"申请结果");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_applyforsuccessful;
    }
}
