package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 酒店订单待评价详情界面
 */
public class HotelorderevaluationdetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "订单详情");
        Button btn_hotelordergotoevaluationdetail = findViewById(R.id.btn_hotelordergotoevaluationdetail);
        btn_hotelordergotoevaluationdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, HotelordergotoevaluationActivity.class);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hotelorderevaluationdetails;
    }
}
