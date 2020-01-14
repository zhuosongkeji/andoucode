package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

public class RestaurantRefundActivity extends BaseActivity {

    @BindView(R.id.reasonEdt)
    EditText reasonEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "取消预约");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_refund;
    }

    public static void start(Activity activity, String id, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, id);
        ActivityUtils.startActivityForResult(bundle, activity, RestaurantRefundActivity.class, requestCode);
    }

    @OnClick(R.id.submitBtn)
    void onSubmitBtnClick() {
        RequestUtil.request(mActivity, true, false,
                () -> ApiUtils.getApiService().refund(
                        LoginInfoUtil.getUid(),
                        getIntent().getStringExtra(KEY_DATA),
                        reasonEdt.getText().toString()
                ), result -> {
                    setResult(Activity.RESULT_OK);
                    finish();
                });
    }
}
