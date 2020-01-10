package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.FoodAdapter;
import com.zskjprojectj.andouclient.adapter.restaurant.FoodCategoryListAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.FoodCategory;
import com.zskjprojectj.andouclient.model.Restaurant;

import java.util.ArrayList;

public class FoodListFragment extends BaseFragment {

    private Restaurant restaurant;

    FoodCategoryListAdapter categoryAdapter = new FoodCategoryListAdapter();
    FoodAdapter foodAdapter = new FoodAdapter();

    public FoodListFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        RecyclerView foodRecyclerView = view.findViewById(R.id.foodRecyclerView);
        foodAdapter.bindToRecyclerView(foodRecyclerView);
        categoryAdapter.bindToRecyclerView(categoryRecyclerView);
        categoryAdapter.setOnItemClickListener((adapter1, view, position) -> {
            FoodCategory category = categoryAdapter.getItem(position);
            if (categoryAdapter.selectMap.get(category)) return;
            categoryAdapter.setSelectedAll(false);
            categoryAdapter.setSelected(category, true);
            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(mActivity) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            smoothScroller.setTargetPosition(category.index);
            foodRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
        });
        foodRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Food food = foodAdapter.getItem(((LinearLayoutManager) foodRecyclerView.getLayoutManager()).findFirstVisibleItemPosition());
                if (food != null && food.headerPosition != -1) {
                    RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(mActivity) {
                        @Override
                        protected int getVerticalSnapPreference() {
                            return LinearSmoothScroller.SNAP_TO_START;
                        }
                    };
                    smoothScroller.setTargetPosition(food.headerPosition);
                    categoryRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
                    categoryAdapter.setSelected(food.category, true, true);
                }
            }
        });
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getFoodCategory(restaurant.id),
                result -> {
                    ArrayList<Food> foods = new ArrayList<>();
                    categoryAdapter.setNewData(result.data);
                    categoryAdapter.setSelected(categoryAdapter.getItem(0), true);
                    for (int i = 0; i < categoryAdapter.getData().size(); i++) {
                        FoodCategory foodCategory = categoryAdapter.getData().get(i);
                        foodCategory.index = foods.size();
                        for (int x = 0; x < foodCategory.foods.size(); x++) {
                            Food food = foodCategory.foods.get(x);
                            if (x == 0) {
                                food.isHeader = true;
                            }
                            food.headerPosition = i;
                            food.category = foodCategory;
                        }
                        foods.addAll(foodCategory.foods);
                    }
                    foodAdapter.setNewData(foods);
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_food_list;
    }
}
