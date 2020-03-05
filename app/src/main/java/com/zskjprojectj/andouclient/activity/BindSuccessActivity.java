package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

import butterknife.BindView;

public class BindSuccessActivity extends BaseActivity {
    @BindView(R.id.btn_jumpmain)
    Button btn_jumpmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "分享成功");
        btn_jumpmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(mActivity, AppHomeActivity.class);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bindsuccess;
    }
}
