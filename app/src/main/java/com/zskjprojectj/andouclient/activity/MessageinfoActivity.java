package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
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
        HttpRxObservable.getObservable(ApiUtils.getApiService().information(id, LoginInfoUtil.getUid()))
                .subscribe(new BaseObserver<InformationBean>(mActivity) {
                    @Override
                    public void onHandleSuccess(InformationBean informationBean) throws IOException {
                        tv_infodetails.setText(informationBean.getTitle());
                        tv_contentdetails.setText("\t\t\t" + informationBean.getContent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_messageinfo;
    }
}
