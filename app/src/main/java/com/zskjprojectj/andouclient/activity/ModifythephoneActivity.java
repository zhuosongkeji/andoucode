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
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

/**
 * 修改绑定手机号码
 */
public class ModifythephoneActivity extends BaseActivity {
    private CountDownTimerUtils countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "修改手机号");
        EditText mobileEdt = findViewById(R.id.modify_phonenum_edittext);
        EditText codeEdt = findViewById(R.id.modify_inputyanzhenma_edittext);
        EditText passwordEdt = findViewById(R.id.modify_pwd_edittext);
        Button modify_yanzhenma_button = findViewById(R.id.modify_yanzhenma_button);
        modify_yanzhenma_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileStr = mobileEdt.getText().toString().trim();
                if (mobileStr.isEmpty()) {
                    ToastUtils.showShort("请输入正确的手机号码!");
                    return;
                }
                countDownTimer = new CountDownTimerUtils(modify_yanzhenma_button, 60000, 1000);
                countDownTimer.start();
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().sendCode(mobileStr, "0"),
                        result -> ToastUtils.showShort("验证码短信已发送,请注意查收!"));
            }
        });
        findViewById(R.id.modify_button).setOnClickListener(v -> {
            String mobileStr = mobileEdt.getText().toString().trim();
            String codeStr = codeEdt.getText().toString().trim();
            String passwordStr = passwordEdt.getText().toString();
            if (mobileStr.isEmpty()) {
                ToastUtils.showShort("请输入正确的手机号码!");
                return;
            }
            if (codeStr.isEmpty()) {
                ToastUtils.showShort("请输入正确的验证码!");
                return;
            }
            if (passwordStr.isEmpty()) {
                ToastUtils.showShort("请输入正确的密码!");
                return;
            }
            RequestUtil.request(mActivity, true, false,
                    () -> ApiUtils.getApiService().upmodel(
                            LoginInfoUtil.getUid(),
                            LoginInfoUtil.getToken(),
                            mobileStr,
                            codeStr
                    ),
                    result -> {
                        ToastUtils.showShort("修改成功,请登录!");
                        finish();
                    });
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifythephone;
    }
}
