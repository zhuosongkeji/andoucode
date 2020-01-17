package com.zskjprojectj.andouclient.activity.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.ui.HeaderItemDecoration;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantCategoryAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.model.RestaurantCategory;
import com.zskjprojectj.andouclient.utils.CategoryFilterPopupWindow;

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

    private CategoryFilterPopupWindow popupView;
    private RestaurantCategory filterCategory = new RestaurantCategory();
    PageLoadUtil<Restaurant> pageLoadUtil;

    private String sort = null;

    @BindView(R.id.defaultSortBtn)
    View defaultSortBtn;
    @BindView(R.id.saleSortBtn)
    View saleSortBtn;
    @BindView(R.id.starsSortBtn)
    View starsSortBtn;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarLightMode(mActivity, true);
        BarUtils.transparentStatusBar(mActivity);
        adapter.bindToRecyclerView(findViewById(R.id.recyclerView));
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            RestaurantCategory category = adapter.getItem(position);
            RestaurantSearchActivity.start(category);
        });
        restaurantAdapter.setOnItemClickListener((adapter, view, position)
                -> RestaurantDetailActivity.start(restaurantAdapter.getItem(position).id));
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getFoodCategory()
                , result -> {
                    adapter.setNewData(result.data);
                    findViewById(R.id.categoryFilterBtn).setOnClickListener(v ->
                    {
                        if (popupView != null && popupView.isShow()) return;
                        popupView = (CategoryFilterPopupWindow) new XPopup.Builder(mActivity)
                                .atView(v)
                                .autoOpenSoftInput(true)
                                .asCustom(new CategoryFilterPopupWindow(mActivity, result.data));
                        popupView.show();
                        popupView.adapter.setOnItemClickListener((adapter, view, position) -> {
                            filterCategory = popupView.adapter.getItem(position);
                            popupView.dismiss();
                            setFilter(filterCategory);
                        });
                    });
                });
        pageLoadUtil = PageLoadUtil.get(mActivity, homeRecyclerView, restaurantAdapter,
                findViewById(R.id.refreshLayout));
        pageLoadUtil.load(() -> ApiUtils.getApiService().getRestaurants(null,
                filterCategory.id,
                sort,
                pageLoadUtil.page),
                (refresh, data) -> {
                    if (refresh && data.size() > 0) {
                        data.get(0).isHeader = true;
                    }
                });
        defaultSortBtn.setSelected(true);
        defaultSortBtn.setOnClickListener(v -> {
            if (defaultSortBtn.isSelected()) return;
            defaultSortBtn.setSelected(!defaultSortBtn.isSelected());
            if (defaultSortBtn.isSelected()) {
                sort = null;
                pageLoadUtil.refresh();
                saleSortBtn.setSelected(false);
                starsSortBtn.setSelected(false);
            }
        });
        saleSortBtn.setOnClickListener(v -> {
            if (saleSortBtn.isSelected()) return;
            saleSortBtn.setSelected(!saleSortBtn.isSelected());
            if (saleSortBtn.isSelected()) {
                sort = "volume";
                pageLoadUtil.refresh();
                defaultSortBtn.setSelected(false);
                starsSortBtn.setSelected(false);
            }
        });
        starsSortBtn.setOnClickListener(v -> {
            if (starsSortBtn.isSelected()) return;
            starsSortBtn.setSelected(!starsSortBtn.isSelected());
            if (starsSortBtn.isSelected()) {
                sort = "stars";
                pageLoadUtil.refresh();
                defaultSortBtn.setSelected(false);
                saleSortBtn.setSelected(false);
            }
        });
    }

    private void setFilter(RestaurantCategory filterCategory) {
        ViewUtil.setText(mActivity, R.id.categoryFilterTxt, filterCategory.name);
        pageLoadUtil.refresh();
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
