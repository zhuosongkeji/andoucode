package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 饭店立即预订
 */
public class OrederSubmitbooking extends BaseActivity {
    private Button btn_post;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_submitthebooking);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("提交预订");
    }

    @Override
    protected void initViews() {
        btn_post=findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(OrdersuccessfullyActivity.class);
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
