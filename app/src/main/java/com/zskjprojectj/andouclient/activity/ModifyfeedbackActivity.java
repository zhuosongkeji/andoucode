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

public class ModifyfeedbackActivity extends BaseActivity {
    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;
    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;
    private Button btn_postcontext;
    private EditText et_context;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifyfeedback);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("意见反馈");
        btn_postcontext=findViewById(R.id.btn_postcontext);
        et_context=findViewById(R.id.et_context);
    }

    @Override
    public void getDataFromServer() {
        btn_postcontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=et_context.getText().toString().trim();
                if (TextUtils.isEmpty(content))
                {
                    ToastUtil.showToast("请输入您的意见信息");
                    return;
                }
                HttpRxObservable.getObservable(ApiUtils.getApiService().opinionindex(LoginInfoUtil.getUid(),LoginInfoUtil.getToken(),content)).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("提交成功");
                    }
                });
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }
}
