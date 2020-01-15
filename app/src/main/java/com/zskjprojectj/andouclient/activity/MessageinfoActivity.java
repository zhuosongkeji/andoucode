package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.InformationBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageinfoActivity extends BaseActivity {
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    private String id;
    private TextView tv_infodetails,tv_contentdetails;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_messageinfo);
    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("信息详情");
        id = getIntent().getStringExtra("id");
        tv_infodetails=findViewById(R.id.tv_infodetails);
        tv_contentdetails=findViewById(R.id.tv_contentdetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().information(id, LoginInfoUtil.getUid())).subscribe(new BaseObserver<InformationBean>(mAt) {
            @Override
            public void onHandleSuccess(InformationBean informationBean) throws IOException {
                tv_infodetails.setText(informationBean.getTitle());
                tv_contentdetails.setText(informationBean.getContent());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }
}
