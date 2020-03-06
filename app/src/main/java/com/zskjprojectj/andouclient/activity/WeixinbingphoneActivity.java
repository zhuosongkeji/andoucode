package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.User;
import com.zskjprojectj.andouclient.utils.CountDownTimerUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

import static com.zskjprojectj.andouclient.utils.ConstantKt.KEY_FOR_RESULT;

public class WeixinbingphoneActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "绑定手机号");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nickname = bundle.getString("nickname");
        String useravator = bundle.getString("avatorpic");
        String useropenid = bundle.getString("openid");
        EditText et_bindphonenum = findViewById(R.id.et_bindphonenum);
        EditText bind_inputyanzhenma_edittext = findViewById(R.id.bind_inputyanzhenma_edittext);
        EditText bind_pwd_edittext = findViewById(R.id.bind_pwd_edittext);
        Button bind_yanzhenma_button = findViewById(R.id.bind_yanzhenma_button);
        Button bind_button = findViewById(R.id.bind_button);
        bind_yanzhenma_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = et_bindphonenum.getText().toString().trim();
                if (mobileStr.isEmpty()) {
                    ToastUtil.showToast("请输入正确的手机号码!");
                    return;
                }
                CountDownTimerUtils countDownTimer = new CountDownTimerUtils(bind_yanzhenma_button, 60000, 1000);
                countDownTimer.start();
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().sendCode(mobileStr, "0"),
                        result -> {
                            ToastUtil.showToast("验证码短信已发送,请注意查收!");
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
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().bindlogin(
                                mobileStr,
                                passwordStr,
                                codeStr,
                                nickname,
                                useropenid,
                                useravator),
                        result -> {
                            LoginInfoUtil.saveLoginInfo(result.data.id, result.data.token);
                            if (!getIntent().getBooleanExtra(KEY_FOR_RESULT, false)) {
                                ActivityUtils.startActivity(mActivity, AppHomeActivity.class);
                            }
                            finish();
                        });
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_weixinbingphone;
    }
}
