package com.zskjprojectj.andouclient.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MallPaySuccessActivity extends BaseActivity {

    @BindView(R.id.btn_see_order)
    Button btn_see_order;

    @BindView(R.id.tv_hintText)
    TextView tv_hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "支付成功");
        String type = getIntent().getStringExtra("type");
        if ("tieBa".equals(type)) {
            btn_see_order.setText("我的发布");
            tv_hintText.setText("发布成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.btn_see_order})
    public void clickBack(View view) {
        switch (view.getId()) {
            case R.id.btn_see_order:
                finish();
                break;
            default:
                break;
        }
    }

    public static void start(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        ActivityUtils.startActivity(bundle, MallPaySuccessActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mall_pay_success;
    }
}
