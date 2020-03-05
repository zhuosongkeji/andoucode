package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 酒店订单取消详情界面
 */
public class HotelordercancledetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity,"订单详情");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hotelordercancledetails;
    }
}
