package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 饭店下单结果
 */
public class OrdersuccessfullyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "下单成功");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ordersuccessfully;
    }
}
