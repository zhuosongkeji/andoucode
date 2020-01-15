package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifythenicknameActivity extends BaseActivity {
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    private EditText infoEdt;
    private TextView confirmBtn;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifythenickname);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("修改昵称");
        infoEdt=findViewById(R.id.infoEdt);
        confirmBtn=findViewById(R.id.confirmBtn);
    }

    @Override
    public void getDataFromServer() {

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=infoEdt.getText().toString().trim();
                if (TextUtils.isEmpty(username))
                {
                    ToastUtil.showToast("请输入昵称");
                    return;
                }
                HttpRxObservable.getObservable(ApiUtils.getApiService().user_head(LoginInfoUtil.getUid(),LoginInfoUtil.getToken(),username)).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("修改成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
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
