package com.zskjprojectj.andouclient.activity.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.ui.HeaderItemDecoration;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.HomeAdapter;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantCategoryAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;

import butterknife.BindView;
import butterknife.OnClick;

public class RestaurantHomeActivity extends BaseActivity {

    RestaurantCategoryAdapter adapter = new RestaurantCategoryAdapter();
    HomeAdapter homeAdapter = new HomeAdapter();

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
        homeAdapter.addHeaderView(
                LayoutInflater.from(mActivity).inflate(R.layout.layout_restaurant_header_view, null));
        adapter.bindToRecyclerView(homeAdapter.getHeaderLayout().findViewById(R.id.recyclerView));
        homeRecyclerView.addItemDecoration(new HeaderItemDecoration(homeRecyclerView, homeAdapter));
        homeAdapter.setOnItemClickListener((adapter, view, position)
                -> RestaurantDetailActivity.start(homeAdapter.getItem(position).id));
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getFoodCategory()
                , result -> adapter.setNewData(result.data));
        PageLoadUtil<Restaurant> pageLoadUtil = PageLoadUtil.get(mActivity, homeRecyclerView, homeAdapter,
                findViewById(R.id.refreshLayout));
        pageLoadUtil.load(() -> ApiUtils.getApiService().getRestaurants(),
                (refresh, data) -> {
                    if (refresh && data.size() > 0) {
                        data.get(0).isHeader = true;
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_home;
    }
}
