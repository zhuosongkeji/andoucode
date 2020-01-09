package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
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
            foodRecyclerView.getLayoutManager().scrollToPosition(category.index);
        });
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getFoodCategory(restaurant.id),
                result -> {
                    categoryAdapter.setNewData(result.data);
                    categoryAdapter.setSelected(categoryAdapter.getItem(0), true);
                    for (int i = 0; i < categoryAdapter.getData().size(); i++) {
                        FoodCategory foodCategory = categoryAdapter.getData().get(i);
                        foodAdapter.getItem(i * 5).isHeader = true;
                        foodAdapter.getItem(i * 5).category = foodCategory;
                        foodCategory.index = i * 5;
                        foodAdapter.notifyDataSetChanged();
                    }
                });
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
        foodAdapter.addData(new Food());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_food_list;
    }
}
