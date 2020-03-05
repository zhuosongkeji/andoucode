package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 待收货商品详情
 */
public class ShoporderforthegoodsdetailsActivity extends BaseActivity {
    private Button btn_checkthelogistics, btn_getgoods, btn_forthegoodsrefund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "商城订单");
        btn_checkthelogistics = findViewById(R.id.btn_checkthelogistics);
        btn_forthegoodsrefund = findViewById(R.id.btn_forthegoodsrefund);
        btn_forthegoodsrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, ShopordersendetailsrefundActivity.class);
            }
        });
        btn_checkthelogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, CheckthelogisticsActivity.class);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_shoporderforthegoodsdetails;
    }
}
