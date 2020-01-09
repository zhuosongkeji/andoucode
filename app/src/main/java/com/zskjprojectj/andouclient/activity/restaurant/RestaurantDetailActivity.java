package com.zskjprojectj.andouclient.activity.restaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.FoodListFragment;
import com.zskjprojectj.andouclient.fragment.RestaurantInfoFragment;
import com.zskjprojectj.andouclient.fragment.ReviewListFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;

import java.util.ArrayList;

import butterknife.BindView;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

public class RestaurantDetailActivity extends BaseActivity {

    ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.fixContainer)
    View fixContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.transparentStatusBar(mActivity);
        BarUtils.setStatusBarLightMode(mActivity, false);
        ActionBarUtil.setTitle(mActivity, "商家详情", false);
        ActionBarUtil.getBackground(mActivity, false).setAlpha(0);
        ((AppBarLayout) findViewById(R.id.appBarLayout))
                .addOnOffsetChangedListener((appBarLayout, verticalOffset) ->
                        ActionBarUtil.getBackground(mActivity, false)
                                .setAlpha(Math.abs(verticalOffset) * 0.01f));
        fixContainer.setPadding(fixContainer.getPaddingLeft(), BarUtils.getStatusBarHeight(), fixContainer.getPaddingEnd(), 0);
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra(KEY_DATA);
        fragments.add(new FoodListFragment(restaurant));
        fragments.add(new ReviewListFragment());
        fragments.add(new RestaurantInfoFragment());
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getRestaurantDetail(restaurant.id),
                result -> bindRestaurant(result.data));
        ((SlidingTabLayout) findViewById(R.id.tabLayout)).setViewPager(
                findViewById(R.id.viewPager),
                new String[]{"预订", "评论", "商家"},
                mActivity,
                fragments);
    }

    private void bindRestaurant(Restaurant restaurant) {
        ViewUtil.setText(mActivity, R.id.nameTxt, restaurant.name);
        ViewUtil.setText(mActivity, R.id.likeTxt, restaurant.praise_num);
        ViewUtil.setText(mActivity, R.id.addressTxt, restaurant.address);
        Glide.with(mActivity)
                .load(restaurant.door_img)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_placeholder)
                        .centerCrop())
                .into((ImageView) findViewById(R.id.bannerImg));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_detail;
    }

    public static void start(Restaurant restaurant) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, restaurant);
        ActivityUtils.startActivity(bundle, RestaurantDetailActivity.class);
    }
}
