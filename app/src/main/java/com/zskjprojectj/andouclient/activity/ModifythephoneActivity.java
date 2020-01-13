package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
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

/**
 * 修改绑定手机号码
 */
public class ModifythephoneActivity extends BaseActivity {
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifythephone);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("修改手机号");
        EditText mobileEdt = findViewById(R.id.modify_phonenum_edittext);
        EditText codeEdt = findViewById(R.id.modify_inputyanzhenma_edittext);
        EditText passwordEdt = findViewById(R.id.modify_pwd_edittext);
        findViewById(R.id.modify_yanzhenma_button)
                .setOnClickListener(v -> {
                    String mobileStr = mobileEdt.getText().toString().trim();
                    if (mobileStr.isEmpty()) {
                        ToastUtil.showToast("请输入正确的手机号码!");
                        return;
                    }
                    HttpRxObservable.getObservable(ApiUtils.getApiService().sendCode(mobileStr, "0"))
                            .subscribe(new BaseObserver<Object>(mAt) {
                                @Override
                                public void onHandleSuccess(Object o) throws IOException {
                                    ToastUtil.showToast("验证码短信已发送,请注意查收!");
                                }
                            });
                });
        findViewById(R.id.modify_button).setOnClickListener(v -> {
            String mobileStr = mobileEdt.getText().toString().trim();
            String codeStr = codeEdt.getText().toString().trim();
            String passwordStr = passwordEdt.getText().toString();
            if (mobileStr.isEmpty()) {
                ToastUtil.showToast("请输入正确的手机号码!");
                return;
            }
            if (codeStr.isEmpty()) {
                ToastUtil.showToast("请输入正确的验证码!");
                return;
            }
            if (passwordStr.isEmpty()) {
                ToastUtil.showToast("请输入正确的密码!");
                return;
            }
            HttpRxObservable.getObservable(ApiUtils.getApiService().upmodel(LoginInfoUtil.getUid(),LoginInfoUtil.getToken(),
                    mobileStr,
                    codeStr
            )).subscribe(new BaseObserver<Object>(mAt) {
                @Override
                public void onHandleSuccess(Object o) throws IOException {
                    ToastUtil.showToast("修改成功,请登录!");
                    finish();
                }
            });
        });
    }

    @Override
    public void getDataFromServer() {

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
