package com.zskjprojectj.andouclient.activity.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.ui.HeaderItemDecoration;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantCategoryAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.model.RestaurantCategory;

import butterknife.BindView;
import butterknife.OnClick;

public class RestaurantHomeActivity extends BaseActivity {

    RestaurantCategoryAdapter adapter = new RestaurantCategoryAdapter();
    RestaurantAdapter restaurantAdapter = new RestaurantAdapter();

    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;

    @OnClick(R.id.backBtn)
    void onBackBtnClick() {
        onBackPressed();
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarLightMode(mActivity, true);
        BarUtils.transparentStatusBar(mActivity);
        restaurantAdapter.addHeaderView(
                LayoutInflater.from(mActivity).inflate(R.layout.layout_restaurant_header_view, null));
        adapter.bindToRecyclerView(restaurantAdapter.getHeaderLayout().findViewById(R.id.recyclerView));
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            RestaurantCategory category = adapter.getItem(position);
            RestaurantSearchActivity.start(category);
        });
        homeRecyclerView.addItemDecoration(new HeaderItemDecoration(homeRecyclerView, restaurantAdapter));
        restaurantAdapter.setOnItemClickListener((adapter, view, position)
                -> RestaurantDetailActivity.start(restaurantAdapter.getItem(position).id));
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getFoodCategory()
                , result -> adapter.setNewData(result.data));
        PageLoadUtil<Restaurant> pageLoadUtil = PageLoadUtil.get(mActivity, homeRecyclerView, restaurantAdapter,
                findViewById(R.id.refreshLayout));
        pageLoadUtil.load(() -> ApiUtils.getApiService().getRestaurants(null, null, pageLoadUtil.page),
                (refresh, data) -> {
                    if (refresh && data.size() > 0) {
                        data.get(0).isHeader = true;
                    }
                });
    }

    @OnClick(R.id.searchBtn)
    void onSearchBtnClick() {
        ActivityUtils.startActivity(RestaurantSearchActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_home;
    }
}
