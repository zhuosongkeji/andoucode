package com.zskjprojectj.andouclient.activity;

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

public class ModifythepasswordActivity extends BaseActivity {
    private CountDownTimerUtils countDownTimer;
    private String nickname, useravator, useropenid;
    private EditText et_setphonenum, set_inputyanzhenma_edittext, set_pwd_edittext;
    private Button set_yanzhenma_button, set_button;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifythepassword);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("修改密码");
    }

    @Override
    protected void initViews() {
        et_setphonenum=findViewById(R.id.et_setphonenum);
        set_inputyanzhenma_edittext=findViewById(R.id.set_inputyanzhenma_edittext);
        set_pwd_edittext=findViewById(R.id.set_pwd_edittext);
        set_yanzhenma_button=findViewById(R.id.set_yanzhenma_button);
        set_button=findViewById(R.id.set_button);
        set_yanzhenma_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = et_setphonenum.getText().toString().trim();
                if (mobileStr.isEmpty()) {
                    ToastUtil.showToast("请输入正确的手机号码!");
                    return;
                }
                countDownTimer = new CountDownTimerUtils(set_yanzhenma_button, 60000, 1000);
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
       set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = et_setphonenum.getText().toString().trim();
                String codeStr = set_inputyanzhenma_edittext.getText().toString().trim();
                String passwordStr = set_pwd_edittext.getText().toString();
                if (mobileStr.isEmpty()) {
                    ToastUtil.showToast("请输入正确的手机号码!");
                    return;
                }
                if (codeStr.isEmpty()) {
                    ToastUtil.showToast("请输入正确的验证码!");
                    return;
                }
                if (passwordStr.isEmpty()) {
                    ToastUtil.showToast("请输入新的密码!");
                    return;
                }

                HttpRxObservable.getObservable(ApiUtils.getApiService().forgetPassword(
                        mobileStr,
                        passwordStr,
                        codeStr
                )).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("修改成功,请登录!");
                        finish();
                    }
                });
//                HttpRxObservable.getObservable(ApiUtils.getApiService().bindlogin(mobileStr, passwordStr, codeStr, nickname, useropenid, useravator)).subscribe(new BaseObserver<User>(mAt) {
//                    @Override
//                    public void onHandleSuccess(User user) throws IOException {
//                        LoginInfoUtil.saveLoginInfo(user.id, user.token);
//                        if (!getIntent().getBooleanExtra(KEY_FOR_RESULT, false)) {
//                            jumpActivity(MainActivity.class);
//                        }
//                        finish();
//                    }
//                });
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
