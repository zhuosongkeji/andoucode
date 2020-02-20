package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.User;
import com.zskjprojectj.andouclient.utils.CountDownTimerUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

import static com.zskjprojectj.andouclient.activity.LoginActivity.KEY_FOR_RESULT;

public class WeixinbingphoneActivity extends BaseActivity {
    private CountDownTimerUtils countDownTimer;
    private String nickname, useravator, useropenid;
    private EditText et_bindphonenum, bind_inputyanzhenma_edittext, bind_pwd_edittext;
    private Button bind_yanzhenma_button, bind_button;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_weixinbingphone);
    }

    @Override
    protected void initViews() {
        topView.setTitle("绑定手机号");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nickname = bundle.getString("nickname");
        useravator = bundle.getString("avatorpic");
        useropenid = bundle.getString("openid");
        et_bindphonenum = findViewById(R.id.et_bindphonenum);
        bind_inputyanzhenma_edittext = findViewById(R.id.bind_inputyanzhenma_edittext);
        bind_pwd_edittext = findViewById(R.id.bind_pwd_edittext);
        bind_yanzhenma_button = findViewById(R.id.bind_yanzhenma_button);
        bind_button=findViewById(R.id.bind_button);
        bind_yanzhenma_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = et_bindphonenum.getText().toString().trim();
                if (mobileStr.isEmpty()) {
                    ToastUtil.showToast("请输入正确的手机号码!");
                    return;
                }
                countDownTimer = new CountDownTimerUtils(bind_yanzhenma_button, 60000, 1000);
                countDownTimer.start();
                HttpRxObservable.getObservable(ApiUtils.getApiService().sendCode(mobileStr, "0"))
                        .subscribe(new BaseObserver<Object>(mAt) {
                            @Override
                            public void onHandleSuccess(Object o) throws IOException {
                                ToastUtil.showToast("验证码短信已发送,请注意查收!");
                            }
                        });
            }
        });
        bind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = et_bindphonenum.getText().toString().trim();
                String codeStr = bind_inputyanzhenma_edittext.getText().toString().trim();
                String passwordStr = bind_pwd_edittext.getText().toString();
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

                HttpRxObservable.getObservable(ApiUtils.getApiService().bindlogin(mobileStr, passwordStr, codeStr, nickname, useropenid, useravator)).subscribe(new BaseObserver<User>(mAt) {
                    @Override
                    public void onHandleSuccess(User user) throws IOException {
                        LoginInfoUtil.saveLoginInfo(user.id, user.token);
                        if (!getIntent().getBooleanExtra(KEY_FOR_RESULT, false)) {
                            jumpActivity(AppHomeActivity.class);
                        }
                        finish();
                    }
                });
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
