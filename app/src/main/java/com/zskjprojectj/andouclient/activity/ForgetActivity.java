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
import com.zskjprojectj.andouclient.utils.CountDownTimerUtils;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.view.TopView;

import java.io.IOException;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/23
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class ForgetActivity extends BaseActivity {
    private TopView topView;
    private CountDownTimerUtils countDownTimer;
    //设置布局文件
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_forget);
    }

    //设置配置数据可以添加某些权限，这几个方法在使用的时候特别注意执行顺序
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    //初始化对象
    @Override
    protected void initViews() {
        topView = findViewById(R.id.alltopview);
        topView.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        EditText mobileEdt = findViewById(R.id.registered_phonenum_edittext);
        EditText codeEdt = findViewById(R.id.registered_inputyanzhenma_edittext);
        EditText passwordEdt = findViewById(R.id.registered_pwd_edittext);
       Button registered_yanzhenma_button=findViewById(R.id.registered_yanzhenma_button);
       registered_yanzhenma_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String mobileStr = mobileEdt.getText().toString().trim();
               if (mobileStr.isEmpty()) {
                   ToastUtil.showToast("请输入正确的手机号码!");
                   return;
               }
               countDownTimer = new CountDownTimerUtils(registered_yanzhenma_button, 60000, 1000);
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
//        findViewById(R.id.registered_yanzhenma_button)
//                .setOnClickListener(v -> {
//                    String mobileStr = mobileEdt.getText().toString().trim();
//                    if (mobileStr.isEmpty()) {
//                        ToastUtil.showToast("请输入正确的手机号码!");
//                        return;
//                    }
//
//                    HttpRxObservable.getObservable(ApiUtils.getApiService().sendCode(mobileStr, "0"))
//                            .subscribe(new BaseObserver<Object>(mAt) {
//                                @Override
//                                public void onHandleSuccess(Object o) throws IOException {
//                                    ToastUtil.showToast("验证码短信已发送,请注意查收!");
//                                }
//                            });
//                });
        findViewById(R.id.registered_button).setOnClickListener(v -> {
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
        });
    }

    //获取网络数据现成请求在这里处理
    @Override
    public void getDataFromServer() {

    }

    //创建Presenter这个方法可以不管
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
