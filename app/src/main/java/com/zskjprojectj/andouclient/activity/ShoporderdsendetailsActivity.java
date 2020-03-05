package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 待发货订单详情页面
 */
public class ShoporderdsendetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "商城订单");
        Button btn_shopordersendetailsrefund = findViewById(R.id.btn_shopordersendetailsrefund);
        btn_shopordersendetailsrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, ShopordersendetailsrefundActivity.class);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_shoporderdsendetails;
    }
}
