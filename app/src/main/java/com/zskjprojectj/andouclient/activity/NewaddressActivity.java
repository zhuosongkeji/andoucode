package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 新增地址
 */
public class NewaddressActivity extends BaseActivity {
    private RelativeLayout ry_selectaddress;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_newaddress);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("新增地址");
    }

    @Override
    protected void initViews() {
        ry_selectaddress=findViewById(R.id.ry_selectaddress);
        ry_selectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ShareLocationActivity.class);
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
