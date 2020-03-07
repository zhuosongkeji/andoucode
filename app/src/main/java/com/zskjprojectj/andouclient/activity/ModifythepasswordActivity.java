package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.CountDownTimerUtils;


public class ModifythepasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "修改密码");
        EditText et_setphonenum = findViewById(R.id.et_setphonenum);
        EditText set_inputyanzhenma_edittext = findViewById(R.id.set_inputyanzhenma_edittext);
        EditText set_pwd_edittext = findViewById(R.id.set_pwd_edittext);
        Button set_yanzhenma_button = findViewById(R.id.set_yanzhenma_button);
        Button set_button = findViewById(R.id.set_button);
        set_yanzhenma_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = et_setphonenum.getText().toString().trim();
                if (mobileStr.isEmpty()) {
                    ToastUtils.showShort("请输入正确的手机号码!");
                    return;
                }
                CountDownTimerUtils countDownTimer = new CountDownTimerUtils(set_yanzhenma_button, 60000, 1000);
                countDownTimer.start();
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().sendCode(mobileStr, "0"),
                        result -> {
                            ToastUtils.showShort("验证码短信已发送,请注意查收!");
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
                    ToastUtils.showShort("请输入正确的手机号码!");
                    return;
                }
                if (codeStr.isEmpty()) {
                    ToastUtils.showShort("请输入正确的验证码!");
                    return;
                }
                if (passwordStr.isEmpty()) {
                    ToastUtils.showShort("请输入新的密码!");
                    return;
                }
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().forgetPassword(
                                mobileStr,
                                passwordStr,
                                codeStr
                        ),
                        result -> {
                            ToastUtils.showShort("修改成功,请登录!");
                            finish();
                        });
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifythepassword;
    }
}
