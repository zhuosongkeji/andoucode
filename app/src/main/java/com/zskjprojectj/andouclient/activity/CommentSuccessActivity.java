package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

public class CommentSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "评论成功");
        findViewById(R.id.btn_back_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallHomeActivity.Companion.start(0);
            }
        });
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_comment_success;
    }
}
