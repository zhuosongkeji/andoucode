package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.RecyclerViewUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.FoodAdapter;
import com.zskjprojectj.andouclient.adapter.restaurant.FoodCategoryListAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.FoodCategory;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class FoodListFragment extends BaseFragment {

    private Restaurant restaurant;
    private FoodCategoryListAdapter categoryAdapter = new FoodCategoryListAdapter();
    private FoodAdapter foodAdapter = new FoodAdapter();

    public OnCartChangedListener onCartChangedListener;

    public FoodListFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        RecyclerView foodRecyclerView = view.findViewById(R.id.foodRecyclerView);
        RecyclerViewUtil.disableItemAnimator(foodRecyclerView);
        foodAdapter.bindToRecyclerView(foodRecyclerView);
        foodAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Food food = foodAdapter.getItem(position);
            if (view.getId() == R.id.subBtn) {
                food.num -= 1;
            } else if (view.getId() == R.id.addBtn) {
                food.num += 1;
            }
            foodAdapter.notifyItemChanged(position);
            if (onCartChangedListener != null) {
                onCartChangedListener.onCartChanged(foodAdapter.getData());
            }
        });
        foodAdapter.setOnItemClickListener((adapter, view, position) -> {
            Food food = foodAdapter.getItem(position);
            NiceDialog.init()
                    .setLayoutId(R.layout.layout_food_detail)
                    .setConvertListener(new ViewConvertListener() {     //进行相关View操作的回调
                        @Override
                        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                            holder.setText(R.id.nameTxt, food.name);
                            holder.setText(R.id.descriptionTxt, food.remark);
                            holder.setText(R.id.priceTxt, food.price);
                            holder.getConvertView().findViewById(R.id.closeBtn)
                                    .setOnClickListener(v -> dialog.dismiss());
                            Glide.with(mActivity)
                                    .load(UrlUtil.getImageUrl(food.image))
                                    .apply(new RequestOptions().centerCrop().placeholder(R.mipmap.ic_placeholder))
                                    .into((ImageView) holder.getConvertView().findViewById(R.id.img));
                        }
                    })
                    .setDimAmount(0.3f)
                    .setGravity(Gravity.BOTTOM)
                    .show(getChildFragmentManager());
        });
        categoryAdapter.bindToRecyclerView(categoryRecyclerView);
        categoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            FoodCategory category = categoryAdapter.getItem(position);
            if (categoryAdapter.isSelect(category)) return;
            categoryAdapter.setSelected(category, true, true);
            RecyclerViewUtil.smoothScrollToPositionTop(mActivity, foodRecyclerView, category.index);
        });
        foodRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Food food = foodAdapter.getItem(
                        RecyclerViewUtil.getLinearLayoutParams(foodRecyclerView).findFirstVisibleItemPosition());
                if (food != null && food.headerPosition != -1) {
                    RecyclerViewUtil.smoothScrollToPositionTop(mActivity, categoryRecyclerView, food.headerPosition);
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

    public void refresh(Food food) {
        foodAdapter.notifyDataSetChanged();
    }

    public interface OnCartChangedListener {
        void onCartChanged(List<Food> foods);
    }
}
