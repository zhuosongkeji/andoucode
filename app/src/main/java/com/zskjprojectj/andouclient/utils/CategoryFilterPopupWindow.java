package com.zskjprojectj.andouclient.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.FilterCategoryAdapter;
import com.zskjprojectj.andouclient.model.RestaurantCategory;

import java.util.List;

public class CategoryFilterPopupWindow extends PartShadowPopupView {

    public FilterCategoryAdapter adapter = new FilterCategoryAdapter();

    public CategoryFilterPopupWindow(@NonNull Context context, List<RestaurantCategory> categories) {
        super(context);
        RestaurantCategory category = new RestaurantCategory();
        category.name = "全部美食";
        adapter.addData(category);
        adapter.addData(categories);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_category_filter_popup_window;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        adapter.bindToRecyclerView(findViewById(R.id.recyclerView));
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }
}