package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

public class ModifyfeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "意见反馈");
        Button btn_postcontext = findViewById(R.id.btn_postcontext);
        EditText et_context = findViewById(R.id.et_context);
        btn_postcontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_context.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast("请输入您的意见信息");
                    return;
                }
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().opinionindex(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                content),
                        result -> {
                            ToastUtil.showToast("提交成功");
                        });
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifyfeedback;
    }
}
