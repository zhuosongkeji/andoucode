package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 饭店立即预订
 */
public class OrederSubmitbooking extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "提交预订");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_submitthebooking;
    }
}
