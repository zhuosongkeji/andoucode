package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.SlidingTabLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.restaurant.RestaurantOrderListFragment;
import com.zskjprojectj.andouclient.model.RestaurantOrder;

import java.util.ArrayList;

public class RestaurantOrderListActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "饭店预订");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.ALL.stateInt));
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.DAI_ZHI_FU.stateInt));
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.DAI_SHI_YONG.stateInt));
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.DAI_PING_JIA.stateInt));
        ((SlidingTabLayout) findViewById(R.id.tabLayout)).setViewPager(
                findViewById(R.id.viewPager),
                new String[]{"全部订单", "待支付", "待使用", "待评价"},
                mActivity,
                fragments);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_order_list;
    }
}
