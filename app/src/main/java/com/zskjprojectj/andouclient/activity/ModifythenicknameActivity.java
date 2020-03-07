package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

public class ModifythenicknameActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "修改昵称");
        EditText infoEdt = findViewById(R.id.infoEdt);
        TextView confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = infoEdt.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showToast("请输入昵称");
                    return;
                }
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().user_head(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                username),
                        result -> {
                            ToastUtil.showToast("修改成功");
                            finish();
                        });
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifythenickname;
    }
}
