package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 酒店入驻
 */
public class HotelbusinessinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "酒店商家入驻");
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_hotelbusinessin;
    }
}
