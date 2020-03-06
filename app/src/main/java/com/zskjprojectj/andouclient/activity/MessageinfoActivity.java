package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.InformationBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;

public class MessageinfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "信息详情");
        String id = getIntent().getStringExtra("id");
        TextView tv_infodetails = findViewById(R.id.tv_infodetails);
        TextView tv_contentdetails = findViewById(R.id.tv_contentdetails);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().information(id, LoginInfoUtil.getUid()),
                result -> {
                    tv_infodetails.setText(result.data.getTitle());
                    tv_contentdetails.setText("\t\t\t" + result.data.getContent());
                });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_messageinfo;
    }
}
