package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.restaurant.RestaurantOrderListFragment;
import com.zskjprojectj.andouclient.model.RestaurantOrder;

import java.util.ArrayList;

import butterknife.BindView;

public class RestaurantOrderListActivity extends BaseActivity {
    ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "饭店预订");
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.ALL.stateInt));
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.DAI_ZHI_FU.stateInt));
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.DAI_SHI_YONG.stateInt));
        fragments.add(new RestaurantOrderListFragment(RestaurantOrder.STATE.DAI_PING_JIA.stateInt));
        viewPager.setOffscreenPageLimit(3);
        ((SlidingTabLayout) findViewById(R.id.tabLayout)).setViewPager(
                viewPager,
                new String[]{"全部订单", "待支付", "待使用", "待评价"},
                mActivity,
                fragments);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == Activity.RESULT_OK) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof RestaurantOrderListFragment) {
                    ((RestaurantOrderListFragment) fragment).refresh();
                }
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_order_list;
    }
}
